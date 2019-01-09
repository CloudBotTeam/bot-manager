package cn.cloudbot.botmanager.domain.bot.group;

import cn.cloudbot.botmanager.dao.GroupService;
import cn.cloudbot.botmanager.domain.bot.BotStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Indexed;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@NodeEntity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotEntity {
    // 存储的 IP

    @Index
    private String ip;

    private String expose_login_port;
    // container 的id

    private String container_id;

    // 对应的 container_name
    private String container_name;

    private String bot_type;

    private BotStatus botStatus;

    @org.neo4j.ogm.annotation.Id
    private Long uuid;

    private Long lastSaveTime;

    @Relationship(type = "HAS_GROUPS", direction = Relationship.UNDIRECTED)
    @JsonIgnore
    private Set<Group> groups = new LinkedHashSet<>();

    public static BotEntity fromBotContainer(BotContainer container) {
        BotEntity botEntity = new BotEntity();
        botEntity.ip = container.getIp();
        botEntity.expose_login_port = container.getExpose_login_port();
        botEntity.container_id = container.getContainer_id();
        botEntity.container_name = container.getContainer_name();
        botEntity.bot_type = container.getBot_type();
        botEntity.uuid = null;
        botEntity.botStatus = BotStatus.BOOTING;

        // set zero
        botEntity.lastSaveTime = new Long(0);

//        Set<Group> groups = new TreeSet<>();
//        for (Group group:
//            groupService.findAllByBotId(botEntity.getUuid())) {
//            groups.add(group);
//        }

        return botEntity;
    }
}
