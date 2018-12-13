package cn.cloudbot.botmanager.domain.message.recv_event.meta_event;

/**
 * 机器人的 HEART BEAT, 用于确定机器人的状态
 */
public class HeartBeat {
    private String post_type;
    private String meta_event_type;
    private Status status;

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getMeta_event_type() {
        return meta_event_type;
    }

    public void setMeta_event_type(String meta_event_type) {
        this.meta_event_type = meta_event_type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
