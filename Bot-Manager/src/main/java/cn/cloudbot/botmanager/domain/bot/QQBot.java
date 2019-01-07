package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.bot.group.BotContainer;
import cn.cloudbot.botmanager.domain.bot.group.BotEntity;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URL;
import java.util.logging.Logger;

@JsonIgnoreProperties
public class QQBot extends BaseBot {

    private String DockerApiAddress = "docker-api";
    private String DockerApiPort = "5000";

    private String DockerHTTPAPI = "http://" + this.DockerApiAddress + ":" + this.DockerApiPort;

    private Logger logger = Logger.getLogger(QQBot.class.getName());

    // db field


//    应该是 BOOT 阶段初始化的
    private URL remote_url;

    QQBot(Long uid) {
        this.setBot_id(uid);

    }


    @Override
    public void asyncSendData(RobotRecvMessage resp) {
//        ResponseEntity<String> response = restTemplate.put(url, entity);
        logger.info(this.entity.getUuid() + " 发送请求" + resp);
        String target = "http://" + entity.getIp() + ":5700" + "/send_group_msg";
        logger.info("请求目标为： " + target);
        restTemplate.postForObject(target, resp, Object.class);
    }




    @Override
    public String getConnetionUrl() {

        if (this.getBot_ip() == null) {
            return null;
        }
        // TODO: make it visitable with this
//        return this.getBot_id() + ":" + bootContainer.getExpose_login_port();
        return "localhost:" + entity.getExpose_login_port();
    }


    @Override
    protected String getRobot_type() {
        return "qq";
    }

    @Override
    public void BootServiceInContainer() {
        logger.info("启动容器 " + this.getBot_id());
        BotContainer bootContainer = restTemplate.getForObject(this.DockerHTTPAPI + "/create", BotContainer.class);
        logger.info("获得BOOT CONTAINER " + bootContainer);
        this.setStatus(BotStatus.BOOTING);
        this.entity = BotEntity.fromBotContainer(bootContainer);

        this.setBot_ip(this.entity.getIp());

        this.entity.setUuid(this.getBot_id());
        this.entity.setLastSaveTime(System.currentTimeMillis());

        logger.info("boot container: " + bootContainer.toString());
        logger.info("entity: " + entity.toString());

    }

    @Override
    public void DestroyServiceInContainer() {
        logger.info("停止容器 " + this.getBot_id());
        restTemplate.delete(this.DockerHTTPAPI + "/delete/" + entity.getContainer_id());
    }

    @Override
    public BotEntity getEntity() {
        return entity;
    }
}
