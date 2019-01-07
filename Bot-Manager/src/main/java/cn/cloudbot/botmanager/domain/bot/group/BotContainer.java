package cn.cloudbot.botmanager.domain.bot.group;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BotContainer {
    // 存储的 IP
    private String ip;
    private String expose_login_port;
    // container 的id
    private String container_id;
    // 对应的 uuid
    private String container_name;

    private String bot_type;
}
