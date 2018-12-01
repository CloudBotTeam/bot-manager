package cn.maplewish.botmanager.exceptions;

public class RobotNotFound extends RuntimeException {
    public String getRobot_name() {
        return robot_name;
    }

    String robot_name;

    public RobotNotFound(String robot_name) {
        this.robot_name = robot_name;
    }


}