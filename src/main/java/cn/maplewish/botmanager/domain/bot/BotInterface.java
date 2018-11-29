package cn.maplewish.botmanager.domain.bot;

import cn.maplewish.botmanager.domain.message.post_event.StringRespMessage;

interface BotInterface {
    //    async send message
    void asyncSendData(StringRespMessage resp);
    //    获得必要的登录方式：比如扫码链接
    String getConnetionUrl();
//    获得一个生成的 BOT 的名称
    String getBotName();
//    确认机器人的状态
    boolean checkBotStatus();
}