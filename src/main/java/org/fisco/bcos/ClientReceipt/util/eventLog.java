package org.fisco.bcos.ClientReceipt.util;

import jdk.vm.ci.code.Register;
import org.fisco.bcos.sdk.abi.tools.TopicTools;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.eventsub.EventLogParams;
import org.fisco.bcos.sdk.eventsub.EventResource;
import org.fisco.bcos.sdk.eventsub.EventSubscribe;
import org.fisco.bcos.sdk.eventsub.filter.EventLogFilter;
import sun.rmi.runtime.NewThreadAction;

import java.util.ArrayList;
import java.util.List;
public class eventLog {
    EventLogParams params = new EventLogParams();
    int state;
    List<org.fisco.bcos.sdk.model.EventLog> log;
    EventCallback callback = new EventCallback() {
        @Override
        //在推送完成时回调此函数，但是不一定是在显示在IDEA时显示 ，故不一定能sout出来
        public void onReceiveLog(int status, List<org.fisco.bcos.sdk.model.EventLog> logs) {
            state = status;
            log = logs;
        }
    };
    EventSubscribe eventSubscribe = new EventSubscribe() {
        @Override
        public String subscribeEvent(EventLogParams params, EventCallback callback) {
            return null;
        }

        @Override
        public void unsubscribeEvent(String registerID, EventCallback callback) {

        }

        @Override
        public List<EventLogFilter> getAllSubscribedEvent() {
            return null;
        }

        @Override
        public EventResource getEventResource() {
            return null;
        }

        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }
    };

    public void Register(){
        // 全部Event fromBlock设置为"1"
        params.setFromBlock("latest");
        // toBlock设置为"latest"，处理至最新区块继续等待新的区块
        params.setToBlock("latest");
        String addr = "0x773c4a4ae703129c4151d48dd637561d15a9bb29";
        // addresses设置为空数组，匹配所有的合约地址
        ArrayList<String> addresses = new ArrayList<String>();
        addresses.add(addr);
        params.setAddresses(addresses);
        // topics设置为空数组，匹配所有的Event

        ArrayList<Object> topics = new ArrayList<>();
        CryptoSuite invalidCryptoSuite=null;
        TopicTools topicTools = new TopicTools(invalidCryptoSuite);
        topics.add(topicTools.stringToTopic("TransferEvent(int256,string,uint256)"));
        params.setTopics(topics);

        String registerId = eventSubscribe.subscribeEvent(params, callback);
        System.out.println(registerId);
        System.out.println(this.state);
        System.out.println(log);
    }


}