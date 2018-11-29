package cn.maplewish.botmanager.domain.message.post_event;

public class StringRespMessage {
    private String group_id;
    private String message;
    private Boolean auto_escape;

    public Boolean getAuto_escape() {
        return auto_escape;
    }

    public void setAuto_escape(Boolean auto_escape) {
        this.auto_escape = auto_escape;
    }


    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
