//package cn.maplewish.botmanager.receiver.receiverImpl;
//
//import WrappedOutputData;
//
//import BotMessageSender;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

/**
 * 发送消息的 BEAN, 配置对象发送对应的消息
 * TODO: 看看能不能改正常一点 TAT
 */
//@Configuration
//@EnableBinding(value = {BotMessageSender.class})
//public class BotMessageSenderProxy {
//    @Autowired
//    private BotMessageSender sender;
//
//    @Output(BotMessageSender.OUTPUT_CHANNEL)
//    public WrappedOutputData sendMsg(WrappedOutputData data) {
//        if (data == null) {
//            throw new RuntimeException("WrappedOutputData is null");
//        }
//        sender.sendData().send(org.springframework.messaging.support.MessageBuilder.withPayload(data).build());
//    }
//
//    private BotMessageSenderProxy() {
//        super();
//    }
//
//    private static BotMessageSenderProxy proxy = new BotMessageSenderProxy();
//
//    @Bean
//    public BotMessageSenderProxy getSender() {
//        return proxy;
//    }
//}
