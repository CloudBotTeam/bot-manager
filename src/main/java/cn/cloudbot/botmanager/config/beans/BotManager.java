package cn.cloudbot.botmanager.config.beans;

import cn.cloudbot.botmanager.exceptions.RobotNotFound;
import cn.cloudbot.botmanager.domain.bot.BaseBot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BotManager {
    private Map<String, BaseBot> botMap = new ConcurrentHashMap<>();

    public void addBot(BaseBot bot) {
        String name = bot.getBotName();
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
    public Map<String, BaseBot> listBot() {
        return new HashMap<>(botMap);
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

    private BotManager() {}
    
}
