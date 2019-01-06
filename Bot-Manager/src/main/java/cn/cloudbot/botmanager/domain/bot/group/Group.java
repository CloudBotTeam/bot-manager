package cn.cloudbot.botmanager.domain.bot.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Group implements Comparable<Group> {
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    private String group_id;

    private Set<Service> serv_list = new TreeSet<>();

    @JsonIgnore
    private String bot_id;

    @Override
    public int compareTo(@NotNull Group o) {
        if (o.group_id == null || this.group_id == null) {
            throw new RuntimeException("Group " + o + " or " + this + " has no group_id");
        }

        return this.group_id.compareTo(o.group_id);
    }
}
