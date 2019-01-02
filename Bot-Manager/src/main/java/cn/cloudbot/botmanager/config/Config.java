package cn.cloudbot.botmanager.config;


import cn.cloudbot.botmanager.domain.bot.BotManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public BotManager getBotManager() {
        return BotManager.getInstance();
    }


}
