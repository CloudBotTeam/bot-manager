package cn.cloudbot.botmanager.receiver.receiverImpl;

import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.receiver.MessageReceiver;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageReceiver.class)
public class ReceiverImpl {
    @StreamListener(MessageReceiver.CHANNEL)
    private void handleInput(StringRespMessage respMessage) {
        System.out.println("收到数据: " + respMessage.toString());
    }
}
