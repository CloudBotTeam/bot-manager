package cn.cloudbot.botmanager.domain.message.recv_event.event;

public class MessageData {
    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getFile() {
        return file;
    }

    public String getFace() {
        return face;
    }

    public String getQq() {
        return qq;
    }

    //    format for text
    private final String text;
    // format for file
    private final String url;
    private final String file;
//  format for face
    private final String face;
//  format for at
    private final String qq;

    public MessageData(String text, String url, String file, String face, String qq) {
        this.text = text;
        this.url = url;
        this.file = file;
        this.face = face;
        this.qq = qq;
    }
}
