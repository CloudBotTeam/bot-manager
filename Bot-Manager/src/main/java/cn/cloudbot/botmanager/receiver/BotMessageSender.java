package cn.cloudbot.botmanager.receiver;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 发送 ROBOT 生成、打包的消息
 */
public interface BotMessageSender {

    String OUTPUT_CHANNEL = "BotManagerMessage";
//    WrappedOutputData 类型的数据被发送
    @Output(OUTPUT_CHANNEL)
    MessageChannel sendData();
}
