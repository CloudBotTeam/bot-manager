package cn.cloudbot.botmanager.receiver.receiverImpl;

import cn.cloudbot.botmanager.domain.bot.BotManager;
import cn.cloudbot.botmanager.receiver.MessageReceiver;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.logging.Logger;

@EnableBinding(MessageReceiver.class)
public class ReceiverImpl {
    private static Logger logger = Logger.getLogger(ReceiverImpl.class.getName());

    @Autowired
    private BotManager botManager;

    @StreamListener(MessageReceiver.CHANNEL)
    private void handleInput(RobotRecvMessage respMessage) {
        logger.info("收到数据: " + respMessage.toString());
        botManager.receiveMessage(respMessage);
    }
}
