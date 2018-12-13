package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;

interface BotInterface {
    //    async send message
    void asyncSendData(StringRespMessage resp);
    //    获得必要的登录方式：比如扫码链接
    String getConnetionUrl();
//    获得一个生成的 BOT 的名称
    String getBotName();
//    确认机器人的状态 -- 状态自己有一个部分缓存？
    Status checkBotStatus();
}