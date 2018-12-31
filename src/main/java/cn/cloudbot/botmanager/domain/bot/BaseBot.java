package cn.cloudbot.botmanager.domain.bot;


public abstract class BaseBot implements BotInterface {
//      管理的小组
    protected String[] managed_groups;

//    这个估计当成 UID 什么的 来处理了
private String robot_name;

    protected abstract String getRobot_type();

    private String bot_ip;
    private String boot_port;

    public String getBot_ip() {
        return bot_ip;
    }

    public void setBot_ip(String bot_ip) {
        this.bot_ip = bot_ip;
    }

    public String getBoot_port() {
        return boot_port;
    }

    public void setBoot_port(String boot_port) {
        this.boot_port = boot_port;
    }
}
