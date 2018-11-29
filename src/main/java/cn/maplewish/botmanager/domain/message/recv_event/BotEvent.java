package cn.maplewish.botmanager.domain.message.recv_event;

public class BotEvent {
    public MessageData[] getMessage() {
        return message;
    }

    private final MessageData[] message;

    public BotEvent(MessageData[] message, String room, String group_id) {
        this.message = message;
        this.room = room;
        this.group_id = group_id;
    }

    private final String room;
    private final String group_id;

    public String getRoom() {
        return room;
    }

    public String getGroup_id() {
        return group_id;
    }

//    功能相关
    public boolean is_wechat_msg() {
        return room == null || room.isEmpty() ;
    }

    public boolean is_qq_msg() {
        return group_id == null || group_id.isEmpty();
    }
}
