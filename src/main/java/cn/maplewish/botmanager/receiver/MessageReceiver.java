package cn.maplewish.botmanager.receiver;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageReceiver {
//    data: import cn.maplewish.botmanager.domain.message.post_event.StringRespMessage;
    String CHANNEL = "ServiceManagerMessage";

    @Input(CHANNEL)
    SubscribableChannel recvMsg();
}
