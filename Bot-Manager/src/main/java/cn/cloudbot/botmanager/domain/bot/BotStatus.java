package cn.cloudbot.botmanager.domain.bot;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BotStatus {
    BOOTING(0),  // 启动中
    NEED_LOGIN(1), // 需要登录
    RUNNING(2), // 运行 状况 良好
    OFFLINE(3); // 离线了

    private Integer status_name;  // 状态的 名称

    BotStatus(Integer status_name) {
        this.status_name = status_name;
    }

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
