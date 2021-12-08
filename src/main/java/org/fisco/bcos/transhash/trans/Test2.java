package org.fisco.bcos.transhash.trans;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.transhash.contract.Transhash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test2 {
    public void watch(String address){
        // 设置参数

    }


    public static void main(String[] args) throws ContractException {
       Logger logger = LoggerFactory.getLogger(Transhash.class);
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
        Transhash transhash = Transhash.load("0x773c4a4ae703129c4151d48dd637561d15a9bb29",client,cryptoKeyPair);
//        System.out.println(transhash.getContractAddress());
        String contractaddress=transhash.getContractAddress();
//        transhash.clientsend("1222222222","333333333333333333333");

        // System.out.println(contractaddress);
        eventLog e=new eventLog(client);
        e.Register();
       // TransactionReceipt res= transhash.clientsend("12313123","00000000000");
       // System.out.println(res);
//        String addr = "0x773c4a4ae703129c4151d48dd637561d15a9bb29";
//        Test2 test=new Test2();

//
//        EventLogParams params = new EventLogParams();
//
//        // 从最新区块开始，fromBlock设置为"latest"
//        params.setFromBlock("latest");
//        // toBlock设置为"latest"，处理至最新块并继续等待共识出块
//        params.setToBlock("latest");
//        // 合约地址
//        ArrayList<String> addresses = new ArrayList<String>();
//        addresses.add(addr);
//        params.setAddresses(addresses);
//
//        // topic0，匹配 TransferEvent(int256,string,string,uint256) 事件
//        ArrayList<Object> topics = new ArrayList<>();
//        CryptoSuite invalidCryptoSuite = new CryptoSuite(client.getCryptoSuite().getCryptoTypeConfig());
//        TopicTools topicTools = new TopicTools(invalidCryptoSuite);
//        topics.add(topicTools.stringToTopic("image_send(address,string,string)"));
//        params.setTopics(topics);
//        // 注册事件
//        EventCallback callback;
//        callback = new EventCallback() {
//            @Override
//            public void onReceiveLog(int status, List<EventLog> logs) {
//
//            }
//        };
//        EventSubscribe eventSubscribe = null;
//        pp
//        String registerId = eventSubscribe.subscribeEvent(params, callback);
//        System.out.println(registerId);
    }
}
