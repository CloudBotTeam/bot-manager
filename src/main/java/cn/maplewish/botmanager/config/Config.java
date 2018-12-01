package cn.maplewish.botmanager.config;


import cn.maplewish.botmanager.config.beans.BotManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public BotManager getBotManager() {
        return BotManager.getInstance();
    }


}
