//package org.fisco.bcos.transhash.trans;
//
//import org.fisco.bcos.ClientReceipt.contract.ClientReceipt;
//import org.fisco.bcos.sdk.BcosSDK;
//import org.fisco.bcos.sdk.client.Client;
//import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Properties;
//
//
////已经在ClientReceipt中自动生成了类及其相关接口，调用即可
//public class Test {
//    static Logger logger = LoggerFactory.getLogger(Transhash.class);
//
//    private BcosSDK bcosSDK;  //结点
//    private Client client;
//    private CryptoKeyPair cryptoKeyPair;
//
//    //初始化区块链结点
//    public void initialize() throws Exception {
//        // 函数initialize中进行初始化
//        // 初始化BcosSDK
//
//        @SuppressWarnings("resource")
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        bcosSDK = context.getBean(BcosSDK.class);
//
//        // 初始化可向群组1发交易的Client
//        client = bcosSDK.getClient(1);
//        // 随机生成发送交易的公私钥对
//        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
//        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
//        logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());
//        System.out.println(cryptoKeyPair.getAddress());
//
//    }
//
//    //部署合约
//    public void deployAssetAndRecordAddr() {
//        try {
////            Transhash transhash = Transhash.deploy(client, cryptoKeyPair);
//            System.out.println(
//                    " deploy clientReceipt, contract address is " + transhash.getContractAddress());
//
//            recordClientReceiptAddr(transhash.getContractAddress());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            // e.printStackTrace();
//            System.out.println(" deploy Asset contract failed, error message is  " + e.getMessage());
//        }
//    }
//
//    //记录合约地址 将合约地址记录到contract.properties中
//    public void recordClientReceiptAddr(String address) throws FileNotFoundException, IOException {
//        Properties prop = new Properties();
//        prop.setProperty("address", address);
//        final Resource contractResource = new ClassPathResource("contract.properties");
//        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
//        prop.store(fileOutputStream, "contract address");
//
//
//        ClientReceipt clientReceipt = new ClientReceipt(address,client,cryptoKeyPair);
//        clientReceipt.clientsend("12345","4465");
//
//
//        prop.getProperty("address");
//    }

//    public String loadClientReceiptAddr() throws Exception {
//        // load Asset contact address from contract.properties
//        Properties prop = new Properties();
//        final Resource contractResource = new ClassPathResource("contract.properties");
//        prop.load(contractResource.getInputStream());
//
//        String contractAddress = prop.getProperty("address");
//        if (contractAddress == null || contractAddress.trim().equals("")) {
//            throw new Exception(" load ClientReceipt contract address failed, please deploy it first. ");
//        }
//        logger.info(" load ClientReceipt address from contract.properties, address is {}", contractAddress);
//        return contractAddress;
//    }
//    //加载记录到contract.properties中的合约地址 未配置contract.properties，暂时无法使用
////
////    public void queryAssetAmount(String assetAccount) {
////        try {
////            String contractAddress = loadAssetAddr();
////            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
////            Tuple2<BigInteger, BigInteger> result = asset.select(assetAccount);
////            if (result.getValue1().compareTo(new BigInteger("0")) == 0) {
////                System.out.printf(" asset account %s, value %s \n", assetAccount, result.getValue2());
////            } else {
////                System.out.printf(" %s asset account is not exist \n", assetAccount);
////            }
////        } catch (Exception e) {
////            // TODO Auto-generated catch block
////            // e.printStackTrace();
////            logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());
////
////            System.out.printf(" query asset account failed, error message is %s\n", e.getMessage());
////        }
////    }
////
////    public void registerAssetAccount(String assetAccount, BigInteger amount) {
////        try {
////            String contractAddress = loadAssetAddr();
////
////            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
////            TransactionReceipt receipt = asset.register(assetAccount, amount);
////            List<Asset.RegisterEventEventResponse> response = asset.getRegisterEventEvents(receipt);
////            if (!response.isEmpty()) {
////                if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
////                    System.out.printf(
////                            " register asset account success => asset: %s, value: %s \n", assetAccount, amount);
////                } else {
////                    System.out.printf(
////                            " register asset account failed, ret code is %s \n", response.get(0).ret.toString());
////                }
////            } else {
////                System.out.println(" event log not found, maybe transaction not exec. ");
////            }
////        } catch (Exception e) {
////            // TODO Auto-generated catch block
////            // e.printStackTrace();
////
////            logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
////            System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
////        }
////    }
////          clientsend
//
//
////    public void transferAsset(String fromAssetAccount, String toAssetAccount, BigInteger amount) {
////        try {
////            String contractAddress = loadAssetAddr();
////            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
////            TransactionReceipt receipt = asset.transfer(fromAssetAccount, toAssetAccount, amount);
////            List<Asset.TransferEventEventResponse> response = asset.getTransferEventEvents(receipt);
////            if (!response.isEmpty()) {
////                if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
////                    System.out.printf(
////                            " transfer success => from_asset: %s, to_asset: %s, amount: %s \n",
////                            fromAssetAccount, toAssetAccount, amount);
////                } else {
////                    System.out.printf(
////                            " transfer asset account failed, ret code is %s \n", response.get(0).ret.toString());
////                }
////            } else {
////                System.out.println(" event log not found, maybe transaction not exec. ");
////            }
////        } catch (Exception e) {
////            // TODO Auto-generated catch block
////            // e.printStackTrace();
////
////            logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
////            System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
////        }
////    }
////
////    public static void Usage() {
////        System.out.println(" Usage:");
////        System.out.println(
////                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient deploy");
////        System.out.println(
////                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient query account");
////        System.out.println(
////                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient register account value");
////        System.out.println(
////                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient transfer from_account to_account amount");
////        System.exit(0);
////    }
//
//    public static void main(String[] args) throws Exception {
////        if (args.length < 1) {
////            Usage();
////        }
//
//        Test test = new Test();
//        test.initialize();
//        test.deployAssetAndRecordAddr();

//
//        client.recordClientReceiptAddr("0x631cbf48ea1b6c0782c17e2a008cbef39e8a8b4f");
//        client.loadClientReceiptAddr();
//
//        eventLog log = new eventLog();
//        log.Register();



//        switch (args[0]) {
//            case "deploy":
//                client.deployAssetAndRecordAddr();
//                break;
//            case "query":
//                if (args.length < 2) {
//                    Usage();
//                }
//                client.queryAssetAmount(args[1]);
//                break;
//            case "register":
//                if (args.length < 3) {
//                    Usage();
//                }
//                client.registerAssetAccount(args[1], new BigInteger(args[2]));
//                break;
//            case "transfer":
//                if (args.length < 4) {
//                    Usage();
//                }
//                client.transferAsset(args[1], args[2], new BigInteger(args[3]));
//                break;
//            default:
//            {
//                Usage();
//            }
//        }
//        System.exit(0);
//        0x5b3515c419d3ab7ca246be1a538ed3dd2ae63c4d
//    }
//
//}