package cn.maplewish.botmanager.domain.message.receiver_data;

import cn.maplewish.botmanager.domain.message.recv_event.event.MessageData;
import lombok.Data;

@Data
public class WrappedOutputData {
    private MessageData data;
    private String platform;
    private String robot_name;
}
