package cn.cloudbot.botmanager.exceptions;

public class GroupNotFound extends RuntimeException {

    public String getGroup_name() {
        return group_name;
    }

    String group_name;

    public GroupNotFound(String group_name) {
        this.group_name = group_name;
    }

}
