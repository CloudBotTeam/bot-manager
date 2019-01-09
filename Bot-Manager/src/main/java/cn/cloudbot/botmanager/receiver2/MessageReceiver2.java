package cn.cloudbot.botmanager.receiver2;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageReceiver2 {
    //    data: import StringRespMessage;
    String CHANNEL = "ServiceManagerMessage2";

    @Input(CHANNEL)
    SubscribableChannel recvMsg();
}
