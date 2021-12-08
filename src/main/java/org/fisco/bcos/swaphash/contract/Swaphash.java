package org.fisco.bcos.swaphash.contract;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Swaphash extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50610611806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630293e5371461006757806372f1adda146100d0578063907e24101461017f578063e8e045ab1461020f575b600080fd5b34801561007357600080fd5b506100ce600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061030b565b005b3480156100dc57600080fd5b5061017d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610325565b005b34801561018b57600080fd5b50610194610357565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101d45780820151818401526020810190506101b9565b50505050905090810190601f1680156102015780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561021b57600080fd5b506102246103f9565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b8381101561026857808201518184015260208101905061024d565b50505050905090810190601f1680156102955780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156102ce5780820151818401526020810190506102b3565b50505050905090810190601f1680156102fb5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b8060029080519060200190610321929190610540565b5050565b816001908051906020019061033b929190610540565b508060009080519060200190610352929190610540565b505050565b606060028054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103ef5780601f106103c4576101008083540402835291602001916103ef565b820191906000526020600020905b8154815290600101906020018083116103d257829003601f168201915b5050505050905090565b60608060006001818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104955780601f1061046a57610100808354040283529160200191610495565b820191906000526020600020905b81548152906001019060200180831161047857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105315780601f1061050657610100808354040283529160200191610531565b820191906000526020600020905b81548152906001019060200180831161051457829003601f168201915b50505050509050915091509091565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061058157805160ff19168380011785556105af565b828001600101855582156105af579182015b828111156105ae578251825591602001919060010190610593565b5b5090506105bc91906105c0565b5090565b6105e291905b808211156105de5760008160009055506001016105c6565b5090565b905600a165627a7a723058203fd1769838b94e459e7201018d1ee09b2c71ce07180a8802e4a9dd04e330ef6d0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50610611806100206000396000f300608060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635d126208146100675780637ca30542146100d0578063a069ec8e1461017f578063cf95d1851461027b575b600080fd5b34801561007357600080fd5b506100ce600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061030b565b005b3480156100dc57600080fd5b5061017d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610325565b005b34801561018b57600080fd5b50610194610357565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156101d85780820151818401526020810190506101bd565b50505050905090810190601f1680156102055780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561023e578082015181840152602081019050610223565b50505050905090810190601f16801561026b5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561028757600080fd5b5061029061049e565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156102d05780820151818401526020810190506102b5565b50505050905090810190601f1680156102fd5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b8060029080519060200190610321929190610540565b5050565b816001908051906020019061033b929190610540565b508060009080519060200190610352929190610540565b505050565b60608060006001818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103f35780601f106103c8576101008083540402835291602001916103f3565b820191906000526020600020905b8154815290600101906020018083116103d657829003601f168201915b50505050509150808054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561048f5780601f106104645761010080835404028352916020019161048f565b820191906000526020600020905b81548152906001019060200180831161047257829003601f168201915b50505050509050915091509091565b606060028054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105365780601f1061050b57610100808354040283529160200191610536565b820191906000526020600020905b81548152906001019060200180831161051957829003601f168201915b5050505050905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061058157805160ff19168380011785556105af565b828001600101855582156105af579182015b828111156105ae578251825591602001919060010190610593565b5b5090506105bc91906105c0565b5090565b6105e291905b808211156105de5760008160009055506001016105c6565b5090565b905600a165627a7a72305820c22cf2ed50cdeb56162861987168336f52085dfeae71b3101767bfd84436c0580029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"backhashvalue\",\"type\":\"string\"}],\"name\":\"Serversend\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"wm_hashvalue\",\"type\":\"string\"},{\"name\":\"im_hashvalue\",\"type\":\"string\"}],\"name\":\"Clientsend\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"clientget\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"serverget\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_SERVERSEND = "Serversend";

    public static final String FUNC_CLIENTSEND = "Clientsend";

    public static final String FUNC_CLIENTGET = "clientget";

    public static final String FUNC_SERVERGET = "serverget";

    protected Swaphash(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt Serversend(String backhashvalue) {
        final Function function = new Function(
                FUNC_SERVERSEND, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(backhashvalue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

//    public byte[] Serversend(String backhashvalue, TransactionCallback callback) {
//        final Function function = new Function(
//                FUNC_SERVERSEND,
//                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(backhashvalue)),
//                Collections.<TypeReference<?>>emptyList());
//        return asyncExecuteTransaction(function, callback);
//    }

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

    public TransactionReceipt Clientsend(String wm_hashvalue, String im_hashvalue) {
        final Function function = new Function(
                FUNC_CLIENTSEND, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(wm_hashvalue), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(im_hashvalue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

//    public byte[] Clientsend(String wm_hashvalue, String im_hashvalue, TransactionCallback callback) {
//        final Function function = new Function(
//                FUNC_CLIENTSEND,
//                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(wm_hashvalue),
//                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(im_hashvalue)),
//                Collections.<TypeReference<?>>emptyList());
//        return asyncExecuteTransaction(function, callback);
//    }

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

    public String clientget() throws ContractException {
        final Function function = new Function(FUNC_CLIENTGET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public Tuple2<String, String> serverget() throws ContractException {
        final Function function = new Function(FUNC_SERVERGET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue());
    }

    public static Swaphash load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Swaphash(contractAddress, client, credential);
    }

    public static Swaphash deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Swaphash.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
