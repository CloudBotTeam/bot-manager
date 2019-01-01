package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;

import java.util.logging.Logger;

public class WechatBot extends BaseBot {
    private Logger logger = Logger.getLogger(WechatBot.class.getName());
    @Override
    protected String getRobot_type() {
        return "wechat";
    }

    @Override
    public void BootServiceInContainer() {
        logger.info("boot wechat bot");
    }

    @Override
    public void asyncSendData(StringRespMessage resp) {

    }

    @Override
    public String getConnetionUrl() {
        return null;
    }


}
