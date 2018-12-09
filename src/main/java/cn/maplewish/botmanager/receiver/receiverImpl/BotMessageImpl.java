package cn.maplewish.botmanager.receiver.receiverImpl;

import cn.maplewish.botmanager.domain.message.receiver_data.WrappedOutputData;
import cn.maplewish.botmanager.receiver.BotMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.annotation.ServiceActivator;

@EnableBinding(BotMessageSender.class)
public class BotMessageImpl {
    @Autowired
    private BotMessageSender sender;

    @Output("output")
    public WrappedOutputData sendMsg(WrappedOutputData data) {
        return data;
    }
}
