package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class QQBot extends BaseBot {

    private Logger logger = Logger.getLogger(QQBot.class.getName());

    private URL remote_url;
    QQBot(String remote) {
        try {
            this.remote_url = new URL(remote);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void asyncSendData(StringRespMessage resp) {

    }

    @Override
    public String getConnetionUrl() {
        return remote_url.toString();
    }


    @Override
    protected String getRobot_type() {
        return "qq";
    }

    @Override
    public void BootServiceInContainer() {
        logger.info("启动容器 " + this.getBot_name());
    }

    @Override
    public void DestroyServiceInContainer() {
        logger.info("停止容器 " + this.getBot_name());
    }
}
