package cn.maplewish.botmanager.domain.bot;

import cn.maplewish.botmanager.domain.message.post_event.StringRespMessage;

import java.net.MalformedURLException;
import java.net.URL;

public class QQBot extends BaseBot {

    private URL remote_url;
    private QQBot(String remote) {
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
        return null;
    }

    @Override
    public String getBotName() {
        return null;
    }

    @Override
    public boolean checkBotStatus() {
        return false;
    }
}
