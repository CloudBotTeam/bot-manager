package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;

public class WechatBot extends BaseBot {
    @Override
    protected String getRobot_type() {
        return "wechat";
    }

    @Override
    public void asyncSendData(StringRespMessage resp) {

    }

    @Override
    public String getConnetionUrl() {
        return null;
    }

    @Override
    public String getBotName() {
        return null;
    }

    @Override
    public Status checkBotStatus() {
        return null;
    }
}
