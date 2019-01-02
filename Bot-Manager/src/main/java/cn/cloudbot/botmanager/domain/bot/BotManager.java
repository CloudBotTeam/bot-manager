package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.bot.group.Group;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.HeartBeat;
import cn.cloudbot.botmanager.exceptions.EnumValueException;
import cn.cloudbot.botmanager.exceptions.RobotNotFound;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


public class BotManager {
    private Logger logger = Logger.getLogger(BotManager.class.getName());

    private Map<String, BaseBot> botMap = new ConcurrentHashMap<>();
    // 机器人的 IP 到机器人 UID 的 map
    private Map<String, String> ipNameMap = new ConcurrentHashMap<>();

    /**
     * 生命周期检测
     * TODO: impl it.
     */
    @Scheduled(fixedRate = 5000)
    private void checkHeartBeatLifeCycle() {
        logger.info("rate check");

    }

    /**
     * 碰到heart_beat 处理
     * @param robot_ip
     * @param heartBeat
     */
    public void handleHeartBeat(String robot_ip, HeartBeat heartBeat) {
        String robot_name = ipNameMap.get(robot_ip);
        BaseBot bot = getBotWithException(robot_name);
        bot.setStatus(BotStatus.RUNNING);
        bot.saveTimeStamp();
    }

    public void addBot(BaseBot bot) {
        String name = bot.getBot_id();
        botMap.put(name, bot);
    }

    public BaseBot getBot(String botName) {
        return botMap.get(botName);
    }

    /**
     * 会抛出运行时异常的
     * @param botName
     * @return
     */
    public BaseBot getBotWithException(String botName) {
        BaseBot bot = getBot(botName);
        if (bot == null) {
            throw new RobotNotFound(botName);
        }
        return bot;
    }

    public boolean removeBot(String botName) {
        return botMap.remove(botName) == null;
    }

    @SuppressWarnings("unchecked")
    public Collection<BaseBot> listBot() {
        return new HashMap<>(botMap).values();
    }

    private static BotManager _instance;

    public static BotManager getInstance() {
        synchronized (BotManager.class) {
            if (_instance == null) {
                _instance = new BotManager();
            }
        }
        return _instance;
    }

    @PostConstruct
    private synchronized void init() {
        if (!botMap.isEmpty()) {
            return;
        }
        BaseBot bot = createBot("qq");
        bot.setBot_ip("10.0.0.229");
        bot.setStatus(BotStatus.RUNNING);
        Group group = new Group();
        group.setGroup_id("117440534");
        bot.addGroup(group);


        addBot(bot);
    }

    /**
     * create boot without id
     * @param bot_type
     * @return
     */
    public static BaseBot createBot(String bot_type) {
        BaseBot create_bot = null;
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        if (bot_type.equals("wechat")) {
            create_bot = new WechatBot(uuid);
        } else if (bot_type.equals("qq")) {
            create_bot = new QQBot(uuid);
        }
        if (create_bot == null) {
            throw new EnumValueException(bot_type);
        }
//      启动服务
        create_bot.BootServiceInContainer();
        return create_bot;
    }

    private BotManager() {}
    
}
