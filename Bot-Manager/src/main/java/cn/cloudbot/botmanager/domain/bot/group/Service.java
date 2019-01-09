package cn.cloudbot.botmanager.domain.bot.group;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Service implements Comparable<Service>{
    /**
     * serv 的名称
     */
    @Index(unique=true)
    private String serv;

    public String getServ_name() {
        return serv;
    }

    @Id
    @GeneratedValue
    private Long id;


    @Override
    public int compareTo(@NotNull Service o) {
        return this.serv.compareTo(o.serv);
    }
}
