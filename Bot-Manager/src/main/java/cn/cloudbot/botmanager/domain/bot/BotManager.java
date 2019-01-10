package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.dao.BotEntityService;
import cn.cloudbot.botmanager.dao.GroupService;
import cn.cloudbot.botmanager.dao.ServiceDao;
import cn.cloudbot.botmanager.dao.ServicerService;
import cn.cloudbot.botmanager.domain.bot.group.BotEntity;
import cn.cloudbot.botmanager.domain.bot.group.Group;
import cn.cloudbot.botmanager.domain.bot.group.Service;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.HeartBeat;
import cn.cloudbot.botmanager.exceptions.EnumValueException;
import cn.cloudbot.botmanager.exceptions.RobotNotFound;
import cn.cloudbot.common.Message.ServiceMessage.RobotRecvMessage;
import cn.cloudbot.common.Message2.RobotRecvMessage2;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class BotManager {
    private Logger logger = Logger.getLogger(BotManager.class.getName());

//    private Map<Long, BaseBot> botMap = new ConcurrentHashMap<>();
    // 机器人的 IP 到机器人 UID 的 map
    private Map<String, Long> ipNameMap = new ConcurrentHashMap<>();

    /**
     * 生命周期检测
     *
     */
    @Scheduled(fixedRate = 20000)
    private void checkHeartBeatLifeCycle() {
        logger.info("heart_beat check");
        for (BaseBot baseBot:
             listBot()) {
            switch (baseBot.getBotStatus()) {
                case BOOTING:
                    baseBot.saveTimeStamp();
                    botEntityService.save(baseBot.getEntity());
                case RUNNING:
                    if (System.currentTimeMillis() - baseBot.lastSavedTimeStamp > 50000) {
                        logger.info(baseBot.getBot_id() + " was offline in heartbeat check.");
                        baseBot.setStatus(BotStatus.OFFLINE);
                        botEntityService.save(baseBot.getEntity());
                    }
                case OFFLINE:
                    continue;
            }

        }
    }



    /**
     * 碰到heart_beat 处理
     * @param robot_ip
     * @param heartBeat
     */
    public void handleHeartBeat(String robot_ip, HeartBeat heartBeat) {
        Long robot_name = ipNameMap.get(robot_ip);
        BaseBot bot = getBotWithException(robot_name);
        bot.setStatus(BotStatus.RUNNING);
        bot.saveTimeStamp();
        botEntityService.save(bot.getEntity());
    }

    /**
     *
     * @param bot
     */
    public void addBot(BaseBot bot) {
//        Long name = bot.getBot_id();
//        botMap.put(name, bot);
        BotEntity entity = bot.getEntity();
        this.botEntityService.save(entity);
    }


    /**
     *
     * 会抛出运行时异常的
     * @param bot_id
     * @return
     */
    public BaseBot getBotWithException(Long bot_id) {
//        BaseBot bot = getBot(bot_id);
        Optional<BotEntity> botEntity = this.botEntityService.findById(bot_id);

        if (!botEntity.isPresent()) {
            throw new RobotNotFound(bot_id.toString());
        }
        return this.createBotWithBotEntity(botEntity.get());
    }

    /**
     * TODO: connect with database
     * @param botName
     * @return
     */
    public boolean removeBot(Long bot_id) {
        BaseBot bot = getBotWithException(bot_id);
        restTemplate.delete("http://" + DockerApiAddress + ":" + DockerApiPort + "/delete/" + bot.entity.getContainer_id());
        this.botEntityService.deleteById(bot_id);

        return bot != null;
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<BaseBot> listBot() {
//        return new HashMap<>(botMap).values();
        Stream<BaseBot> baseBotStream = botEntityService.findAll().stream().map(this::createBotWithBotEntity);
        return baseBotStream.collect(Collectors.toList());
    }

    private static BotManager _instance;

    /**
     * TODO: create singleton bean.
     * @return
     */
    public static BotManager getInstance() {
        synchronized (BotManager.class) {
            if (_instance == null) {
                _instance = new BotManager();
            }
        }
        return _instance;
    }

    /**
     * todo: make clear how to fix this
     */
    @PostConstruct
    private synchronized void init() {
//        if (!botMap.isEmpty()) {
//            return;
//        }
//        @SuppressWarnings("unchecked")
//        Collection<BootContainer> bootContainers = restTemplate.getForObject("http://" + this.DockerApiAddress + ":" + this.DockerApiPort, Collection.class);
//        for (BootContainer boot_container:
//             bootContainers) {
//
//        }
    }

    @Autowired
    private ServicerService serviceDao;

    public void receiveMessage2(RobotRecvMessage2 robotRecvMessage2) {
        switch (robotRecvMessage2.getType()) {
            case "all":
                logger.info("all message");

                String serv_name = robotRecvMessage2.getFrom_service();
                Service service = serviceDao.findServ(serv_name);
                logger.info("Service 找到了：" + service);
                for ( Group group : groupService.findAll()) {
                    if (!group.getServList().contains(service)) {
                        continue;
                    }
                    logger.info("有 GROUP: " + group);
                    RobotRecvMessage message = new RobotRecvMessage();
                    message.setGroup_id(group.getGroup());
                    for (BaseBot bot:
                         this.listBot()) {
                        logger.info("Bots: " + bot);
                        for (Group group2:
                             bot.group_list) {
                            logger.info("比较的子GROUP 门"+ group2 + " " + group);
                            if (group2.getGroup().equals(group.getGroup())) {
                                logger.info("有 Bot: " + bot);

                                message.setMessage(robotRecvMessage2.getMessage());

                                message.setRoom_id(group.getGroup());

                                bot.asyncSendData(message);
                            }
                        }

                    }
                }
                break;
            case "return":
                logger.info("return message from received " + robotRecvMessage2);
                RobotRecvMessage message = new RobotRecvMessage();
                message.setMessage(robotRecvMessage2.getMessage());
                message.setGroup_id(robotRecvMessage2.getGroup_id());
                message.setRoom_id(message.getGroup_id());
                Optional<BotEntity> botEntityOptional = botEntityService.findByIp(robotRecvMessage2.getRobot_ip());
                if (!botEntityOptional.isPresent()) {
                    return;
                }
                BotEntity botEntity = botEntityOptional.get();
                BaseBot bot = createBotWithBotEntity(botEntity);
                bot.asyncSendData(message);

        }
    }

    public void receiveMessage(RobotRecvMessage robotRecvMessage) {
//        BaseBot bot = this.getBotWithException(robotRecvMessage.getRobot_name());

        for (BaseBot bot:
             listBot()) {
            bot.asyncSendData(robotRecvMessage);
        }

    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BotEntityService botEntityService;

    @Autowired
    private GroupService groupService;
    /**
     * create boot without id
     * @param bot_type
     * @return
     */
    public BaseBot createBot(String bot_type) {
        logger.info("want to create with type " + bot_type);
        bot_type = bot_type.toLowerCase();
        BaseBot create_bot = null;
        // 给BOT 生成唯一的uuid, 成功
        final Long uuid = UUID.randomUUID().getMostSignificantBits() & Integer.MAX_VALUE;
        if (bot_type.equals("wechat")) {
            create_bot = new WechatBot(uuid);
        } else if (bot_type.equals("qq")) {
            create_bot = new QQBot(uuid);
        }

        if (create_bot == null) {
            throw new EnumValueException(bot_type);
        }

        create_bot.setRestTemplate(this.restTemplate);
//      启动服务
        create_bot.BootServiceInContainer();

        // TODO: db lize
        this.addBot(create_bot);
        return create_bot;
    }

    public void deleteAll() {
        botEntityService.deleteAll();
    }

    public BaseBot createBotWithBotEntity(BotEntity entity) {
        if (entity.getBot_type().equals("qq")) {
            return createQQBotWithBotEntity(entity);
        } else if (entity.getBot_type().equals("wechat")) {
            return createWechatBotWithBotEntity(entity);
        }
        throw new EnumValueException(entity.getBot_type());
    }

    private QQBot createQQBotWithBotEntity(BotEntity entity) {
        QQBot qqBot = new QQBot(entity.getUuid());
        qqBot.setStatus(entity.getBotStatus());
        qqBot.setBot_ip(entity.getIp());
        qqBot.group_list = entity.getGroups();
        qqBot.lastSavedTimeStamp = entity.getLastSaveTime();
        qqBot.setRestTemplate(this.restTemplate);
        qqBot.setEntity(entity);
        return qqBot;
    }

    private WechatBot createWechatBotWithBotEntity(BotEntity entity) {
        WechatBot wcBot = new WechatBot(entity.getUuid());
        wcBot.setStatus(entity.getBotStatus());
        wcBot.setBot_ip(entity.getIp());
        wcBot.group_list = entity.getGroups();
        wcBot.lastSavedTimeStamp = entity.getLastSaveTime();
        wcBot.setRestTemplate(this.restTemplate);
        wcBot.setEntity(entity);
        return wcBot;
    }

    private BotManager() {}

    private String DockerApiAddress = "docker-api";
    private String DockerApiPort = "5000";
    
}
