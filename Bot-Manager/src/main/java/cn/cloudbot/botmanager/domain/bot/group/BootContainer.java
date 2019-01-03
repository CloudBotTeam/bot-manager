package cn.cloudbot.botmanager.domain.bot.group;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BootContainer {
    private String ip;
    private String expose_login_port;
    private String container_id;
    private String container_name;

    private String bot_type;
}
