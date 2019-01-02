package cn.cloudbot.botmanager.domain.bot;

public enum BotStatus {
    BOOTING("booting"),  // 启动中
    NEED_LOGIN("required-login"), // 需要登录
    RUNNING("running"), // 运行 状况 良好
    OFFLINE("offline"); // 离线了

    private String status_name;  // 状态的 名称

    BotStatus(String status_name) {
        this.status_name = status_name;
    }


    @Override
    public String toString() {
        return this.status_name;
    }
}
