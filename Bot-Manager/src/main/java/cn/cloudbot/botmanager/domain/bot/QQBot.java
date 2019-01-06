package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.bot.group.BootContainer;
import cn.cloudbot.botmanager.domain.message.post_event.StringRespMessage;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class QQBot extends BaseBot {

    private String DockerApiAddress = "docker-api";
    private String DockerApiPort = "5000";

    private String DockerHTTPAPI = "http://" + this.DockerApiAddress + ":" + this.DockerApiPort;

    private Logger logger = Logger.getLogger(QQBot.class.getName());

    private BootContainer bootContainer;
//    应该是 BOOT 阶段初始化的
    private URL remote_url;

    QQBot(String uid) {
        this.setBot_id(uid);

    }


    @Override
    public void asyncSendData(RobotRecvMessage resp) {
//        ResponseEntity<String> response = restTemplate.put(url, entity);
        logger.info(this.getBot_id() + " 发送请求" + resp);
        String target = "http://" + bootContainer.getIp() + ":5700" + "/send_group_msg";
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
        return "localhost:" + bootContainer.getExpose_login_port();
    }


    @Override
    protected String getRobot_type() {
        return "qq";
    }

    @Override
    public void BootServiceInContainer() {
        logger.info("启动容器 " + this.getBot_id());
        bootContainer = restTemplate.getForObject(this.DockerHTTPAPI + "/create", BootContainer.class);
        logger.info("获得BOOT CONTAINER " + bootContainer);
        this.setStatus(BotStatus.BOOTING);
        this.setBot_ip(bootContainer.getIp());

    }

    @Override
    public void DestroyServiceInContainer() {
        logger.info("停止容器 " + this.getBot_id());
        restTemplate.delete(this.DockerHTTPAPI + "/delete/" + bootContainer.getContainer_id());
    }
}
