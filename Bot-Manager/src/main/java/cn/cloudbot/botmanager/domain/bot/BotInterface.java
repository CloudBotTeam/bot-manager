package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;

interface BotInterface {
    //    async send message
    void asyncSendData(RobotRecvMessage resp);
    //    获得必要的登录方式：比如扫码链接
    String getConnetionUrl();
//    获得一个生成的 BOT 的名称
    Long getBot_id();
//    确认机器人的状态 -- 状态自己有一个部分缓存？
    BotStatus getBotStatus();
}