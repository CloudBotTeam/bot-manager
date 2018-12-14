package cn.cloudbot.botmanager.receiver.receiverImpl;

import cn.cloudbot.botmanager.receiver.MessageReceiver;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageReceiver.class)
public class ReceiverImpl {
    @StreamListener(MessageReceiver.CHANNEL)
    private void handleInput(RobotRecvMessage respMessage) {
        System.out.println("收到数据: " + respMessage.toString());
    }
}
