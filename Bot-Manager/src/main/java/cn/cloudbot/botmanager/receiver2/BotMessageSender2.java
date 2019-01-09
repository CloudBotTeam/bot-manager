package cn.cloudbot.botmanager.receiver2;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BotMessageSender2 {
    String OUTPUT_CHANNEL = "BotMessage2";
    //    WrappedOutputData 类型的数据被发送
    @Output(OUTPUT_CHANNEL)
    MessageChannel sendData();
}
