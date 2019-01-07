package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class WechatBot extends BaseBot {
    private Logger logger = Logger.getLogger(WechatBot.class.getName());
    private URL remote_url;
    public WechatBot(Long uuid) {
//        try {
//            this.remote_url = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        this.setBot_id(uuid);
    }

    @Override
    protected String getRobot_type() {
        return "wechat";
    }

    @Override
    public void BootServiceInContainer() {
        logger.info("boot wechat bot");
    }

    @Override
    public void DestroyServiceInContainer() {
        logger.info("destroy wechat bot");
    }

    @Override
    public void asyncSendData(RobotRecvMessage resp) {

    }

    @Override
    public String getConnetionUrl() {
        return null;
    }


}
