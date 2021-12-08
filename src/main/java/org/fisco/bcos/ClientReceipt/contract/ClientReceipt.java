package org.fisco.bcos.ClientReceipt.contract;

import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.*;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class ClientReceipt extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506103a9806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680633f64f893146100515780635951fe0c146100ba575b600080fd5b34801561005d57600080fd5b506100b8600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610169565b005b3480156100c657600080fd5b50610167600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061023c565b005b7f4d2cc758bea1e227c0b0064643b9cd0cc4a7cf43d7b8e4bd3e0be0845d7824f23382604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156101fe5780820151818401526020810190506101e3565b50505050905090810190601f16801561022b5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a150565b7f6822e270c4db0980f72f2df30c3f8313b7511c0b18b0cfa7bcef5b7e91303569338383604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156102d65780820151818401526020810190506102bb565b50505050905090810190601f1680156103035780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561033c578082015181840152602081019050610321565b50505050905090810190601f1680156103695780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a150505600a165627a7a723058208e13c09f95727ca97f6fa944ed70fb8184e1dd5bfef8b9b0d79bd6a606dc759b0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506103a9806100206000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680636944ab9d14610051578063b5d2ff8e146100ba575b600080fd5b34801561005d57600080fd5b506100b8600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610169565b005b3480156100c657600080fd5b50610167600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061023c565b005b7f4fc2573e0895dd6169f9a6fc96e69f30872b27533b97585688856fead11596983382604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b838110156101fe5780820151818401526020810190506101e3565b50505050905090810190601f16801561022b5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a150565b7f268f17cf4d81befd5101e8d4db4a0a5e94f2184c3a7b6d3c3fc74fed8b86f13c338383604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156102d65780820151818401526020810190506102bb565b50505050905090810190601f1680156103035780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561033c578082015181840152602081019050610321565b50505050905090810190601f1680156103695780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a150505600a165627a7a723058201116693ab1c5db4e332d01dc11e42adbc4ab1657c9a749a87d8953c5f39dc91d0029"};

   public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"backhashvalue\",\"type\":\"string\"}],\"name\":\"serversend\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"wm_hashvalue\",\"type\":\"string\"},{\"name\":\"im_hashvalue\",\"type\":\"string\"}],\"name\":\"clientsend\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"watermarkhash\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"imagehash\",\"type\":\"string\"}],\"name\":\"image_send\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"backhash\",\"type\":\"string\"}],\"name\":\"image_back\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_SERVERSEND = "serversend";

    public static final String FUNC_CLIENTSEND = "clientsend";

    public static final Event IMAGE_SEND_EVENT = new Event("image_send",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event IMAGE_BACK_EVENT = new Event("image_back",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public ClientReceipt(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt serversend(String backhashvalue) {
        final Function function = new Function(
                FUNC_SERVERSEND,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(backhashvalue)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void serversend(String backhashvalue, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SERVERSEND,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(backhashvalue)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForServersend(String backhashvalue) {
        final Function function = new Function(
                FUNC_SERVERSEND,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(backhashvalue)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getServersendInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SERVERSEND,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }

    public TransactionReceipt clientsend(String wm_hashvalue, String im_hashvalue) {
        final Function function = new Function(
                FUNC_CLIENTSEND,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(wm_hashvalue),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(im_hashvalue)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void clientsend(String wm_hashvalue, String im_hashvalue, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CLIENTSEND,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(wm_hashvalue),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(im_hashvalue)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForClientsend(String wm_hashvalue, String im_hashvalue) {
        final Function function = new Function(
                FUNC_CLIENTSEND,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(wm_hashvalue),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(im_hashvalue)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getClientsendInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CLIENTSEND,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue()
        );
    }

    public List<Image_sendEventResponse> getImage_sendEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(IMAGE_SEND_EVENT, transactionReceipt);
        ArrayList<Image_sendEventResponse> responses = new ArrayList<Image_sendEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Image_sendEventResponse typedResponse = new Image_sendEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.watermarkhash = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.imagehash = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeImage_sendEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(IMAGE_SEND_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeImage_sendEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(IMAGE_SEND_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public List<Image_backEventResponse> getImage_backEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(IMAGE_BACK_EVENT, transactionReceipt);
        ArrayList<Image_backEventResponse> responses = new ArrayList<Image_backEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            Image_backEventResponse typedResponse = new Image_backEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.backhash = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeImage_backEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(IMAGE_BACK_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeImage_backEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(IMAGE_BACK_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static ClientReceipt load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ClientReceipt(contractAddress, client, credential);
    }

    public static ClientReceipt deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(ClientReceipt.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class Image_sendEventResponse {
        public TransactionReceipt.Logs log;

        public String sender;

        public String watermarkhash;

        public String imagehash;
    }

    public static class Image_backEventResponse {
        public TransactionReceipt.Logs log;

        public String sender;

        public String backhash;
    }
}