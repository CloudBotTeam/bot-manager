package cn.maplewish.botmanager.receiver;

import cn.maplewish.botmanager.domain.message.post_event.StringRespMessage;
import cn.maplewish.botmanager.domain.message.receiver_data.WrappedOutputData;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 发送 ROBOT 生成、打包的消息
 */
public interface BotMessageSender {

    String OUTPUT_CHANNEL = "BotManagerMessage";
//    WrappedOutputData 类型的数据被发送
    @Output("output")
    MessageChannel sendData();
}
