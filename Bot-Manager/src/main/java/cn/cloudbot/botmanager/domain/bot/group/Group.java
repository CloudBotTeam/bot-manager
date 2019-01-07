package cn.cloudbot.botmanager.domain.bot.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.neo4j.ogm.annotation.*;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@NodeEntity
public class Group implements Comparable<Group> {

    @Index
    private String group_id;

    @Relationship(type = "HAS_SERV", direction = Relationship.UNDIRECTED)
    private Set<Service> serv_list = new TreeSet<>();

    @JsonIgnore
    private Long bot_id;

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public int compareTo(@NotNull Group o) {
        if (o.group_id == null || this.group_id == null) {
            throw new RuntimeException("Group " + o + " or " + this + " has no group_id");
        }

        return this.group_id.compareTo(o.group_id);
    }
}
