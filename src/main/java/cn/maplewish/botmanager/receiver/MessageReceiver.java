package cn.maplewish.botmanager.receiver;

import cn.maplewish.botmanager.domain.message.post_event.StringRespMessage;
import org.springframework.cloud.stream.annotation.Input;

public interface MessageReceiver {
    @Input("input")
    StringRespMessage recvMsg();
}
