package cn.cloudbot.botmanager.domain.bot;


import cn.cloudbot.botmanager.domain.bot.group.Group;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseBot implements BotInterface {
    @org.jetbrains.annotations.Contract(pure = true)
    public final Collection<Group> getGroup_list() {
        return group_list;
    }

    /**
     * return false if duplicate
     * @param group
     * @return
     */
    public synchronized boolean addGroup(Group group) {
        if (group_list.contains(group)) {
            return false;
        } else {
            group_list.add(group);
            return true;
        }
    }

    public synchronized boolean removeGroup(Group group) {
        boolean to_return = group_list.contains(group);
        if (to_return) {
            group_list.remove(group);
        }
        return to_return;
    }


    //      管理的小组
    protected Set<Group> group_list = new TreeSet<>();

//    这个估计当成 UID 什么的 来处理了
    private String robot_name;

    protected abstract String getRobot_type();

    private String bot_ip;

    /**
     * 检查机器人的 状态
     * @return
     */
    public final BotStatus getBotStatus() {
        return status;
    }

    public final void setStatus(BotStatus status) {
        this.status = status;
    }

    //    机器人的状态
    private BotStatus status;

    public final String getBot_ip() {
        return bot_ip;
    }

    public final void setBot_ip(String bot_ip) {
        this.bot_ip = bot_ip;
    }

    /**
     * 启动 docker 应用服务，阻塞调用
     */
    public abstract void BootServiceInContainer();

    /**
     * 关闭 docker 应用服务
     */
    public abstract void DestroyServiceInContainer();

    public final String getBot_name() {
        return robot_name;
    }

    public final void setBot_name(String robot_name) {
        this.robot_name = robot_name;
    }
}
