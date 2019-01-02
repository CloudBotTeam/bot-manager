package cn.cloudbot.botmanager.receiver;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageReceiver {
//    data: import StringRespMessage;
    String CHANNEL = "ServiceManagerMessage";

    @Input(CHANNEL)
    SubscribableChannel recvMsg();
}
