package cn.maplewish.botmanager.receiver.receiverImpl;

import cn.maplewish.botmanager.domain.message.post_event.StringRespMessage;
import cn.maplewish.botmanager.receiver.MessageReceiver;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageReceiver.class)
public class ReceiverImpl {
    @StreamListener("input")
    private void handleInput(StringRespMessage respMessage) {
        System.out.println("收到数据");
    }
}
