package cn.cloudbot.botmanager.receiver2.ReceiverImpl2;

import cn.cloudbot.botmanager.domain.bot.BotManager;
import cn.cloudbot.botmanager.receiver.MessageReceiver;
import cn.cloudbot.botmanager.receiver2.MessageReceiver2;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;
import cn.cloudbot.common.Message2.RobotRecvMessage2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.logging.Logger;

@EnableBinding(MessageReceiver2.class)
public class ReceiverImpl2 {
    private static Logger logger = Logger.getLogger(cn.cloudbot.botmanager.receiver.receiverImpl.ReceiverImpl.class.getName());

    @Autowired
    private BotManager botManager;

    @StreamListener(MessageReceiver2.CHANNEL)
    private void handleInput(RobotRecvMessage2 respMessage) {
        logger.info("收到数据: " + respMessage.toString());
        botManager.receiveMessage2(respMessage);
    }
}
