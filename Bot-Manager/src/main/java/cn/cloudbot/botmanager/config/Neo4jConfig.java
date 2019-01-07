package cn.cloudbot.botmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(basePackages = "cn.cloudbot.botmanager.bot.group")
public class Neo4jConfig {

}
