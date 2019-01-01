package cn.cloudbot.botmanager.domain.bot;

import cn.cloudbot.botmanager.domain.bot.group.Group;
import cn.cloudbot.botmanager.exceptions.RobotNotFound;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BotManager {
    private Map<String, BaseBot> botMap = new ConcurrentHashMap<>();

    public void addBot(BaseBot bot) {
        String name = bot.getBot_name();
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
    private void init() {
        QQBot bot = new QQBot("http://localhost:5700");
        bot.setBot_ip("127.0.0.1");
        bot.setBot_name("nmsl");
        bot.setStatus(BotStatus.BOOTING);
        botMap.put(bot.getBot_name(), bot);

        Group group = new Group();
        group.setGroup_id("20193803127");
        bot.addGroup(group);

    }

    private BotManager() {}
    
}
