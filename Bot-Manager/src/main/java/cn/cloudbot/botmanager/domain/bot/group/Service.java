package cn.cloudbot.botmanager.domain.bot.group;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service implements Comparable<Service>{
    /**
     * serv 的名称
     */
    private String serv_id;

    public String getServ_name() {
        return serv_id;
    }


    @Override
    public int compareTo(@NotNull Service o) {
        return this.serv_id.compareTo(o.serv_id);
    }
}
