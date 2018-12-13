package cn.cloudbot.botmanager.domain.message.recv_event.event;

public class MessageSegment {
    public MessageSegment(String type, MessageData data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public MessageData getData() {
        return data;
    }

    private final String type;
    private final MessageData data;

}
