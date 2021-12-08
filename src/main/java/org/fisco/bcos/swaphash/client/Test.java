package org.fisco.bcos.swaphash.client;

import io.ipfs.api.IPFS;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.swaphash.contract.Swaphash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) throws Exception {

        IPFS ipfs=new IPFS("/ip4/127.0.0.1/tcp/5001");
        IpfsFile ipfsfile=new IpfsFile(ipfs);

        Logger logger = LoggerFactory.getLogger(Swaphash.class);
        BcosSDK bcosSDK;  //结点
        Client client;
        CryptoKeyPair cryptoKeyPair;
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        // 初始化可向群组1发交易的Client
        client = bcosSDK.getClient(1);
        // 随机生成发送交易的公私钥对
        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());
        // System.out.println(cryptoKeyPair.getAddress());

//        Swaphash swaphash=Swaphash.deploy(client,cryptoKeyPair);
//        System.out.println(swaphash.getContractAddress());
           String contracthash="0x21b7e02733b966c2377a92d280db210b4699c667";
           Swaphash swaphash=Swaphash.load(contracthash,client,cryptoKeyPair);

           String wmhash=ipfsfile.upload("Clientsendimage/1.jpg");
           String imhash=ipfsfile.upload("Clientsendimage/2.jpg");

           System.out.println(wmhash);
           System.out.println(imhash);

           swaphash.Clientsend(wmhash,imhash);
           System.out.println(swaphash.serverget());
           String im="";
           String wm="";
           wm=swaphash.serverget().getValue1();
           im=swaphash.serverget().getValue2();

           ipfsfile.download("Servertempimage/wm.jpg",wm);
           ipfsfile.download("Servertempimage/im.jpg",im);
           /*
        水印算法。
            */
            String backhash=ipfsfile.upload("Servertempimage/wm.jpg");
           swaphash.Serversend(backhash);
           String re=swaphash.clientget();
           System.out.println(re);
           ipfsfile.download("Clientgetimage/wm.jpg",re);
    }
}
