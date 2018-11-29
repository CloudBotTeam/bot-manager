package cn.maplewish.botmanager.beans;

import cn.maplewish.botmanager.domain.bot.BaseBot;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BotManager {
    private Map<String, BaseBot> botMap = new ConcurrentHashMap<>();

    public void addBot(BaseBot bot) {
        String name = bot.getBotName();
        botMap.put(name, bot);
    }

    public boolean removeBot(String botName) {
        return botMap.remove(botName) == null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, BaseBot> listBot() {
        return new HashMap<>(botMap);
    }

}
