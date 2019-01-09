package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.bot.group.BotContainer;
import cn.cloudbot.botmanager.domain.bot.group.BotEntity;
import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class WechatBot extends BaseBot {
    private Logger logger = Logger.getLogger(WechatBot.class.getName());

    private String DockerApiAddress = "docker-api";
    private String DockerApiPort = "5000";

    private String DockerHTTPAPI = "http://" + this.DockerApiAddress + ":" + this.DockerApiPort;

    public WechatBot(Long uuid) {
//        try {
//            this.remote_url = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        this.setBot_id(uuid);
    }

    @Override
    public BotEntity getEntity() {
        return entity;
    }

    @Override
    protected String getRobot_type() {
        return "wechat";
    }

    @Override
    public void BootServiceInContainer() {
        logger.info("boot wechat");
        logger.info("启动容器 " + this.getBot_id());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.DockerHTTPAPI + "/create")
                .queryParam("type", "wechat");
        BotContainer bootContainer = restTemplate.getForObject(builder.toUriString(), BotContainer.class);
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
        logger.info("destroy wechat bot");
        logger.info("停止容器 " + this.getBot_id());
        restTemplate.delete(this.DockerHTTPAPI + "/delete/" + entity.getContainer_id());
    }

    @Override
    public void asyncSendData(RobotRecvMessage resp) {
        logger.info(this.entity.getUuid() + " 发送请求" + resp);
        String target = "http://" + entity.getIp() + ":5700" + "/send_group_msg";
        logger.info("请求目标为： " + target);
        restTemplate.postForObject(target, resp, Object.class);
    }


    @Override
    public String getConnetionUrl() {
        try {
            String target = "http://" + entity.getIp() + ":5700" + "/connectUrl";
            String connectUrl = restTemplate.getForObject(target , String.class);
            return connectUrl;
        } catch (RuntimeException e) {
            return "";
        }


    }


}
