package cn.cloudbot.botmanager.controller;

import cn.cloudbot.botmanager.domain.bot.BotManager;
import cn.cloudbot.botmanager.domain.bot.BaseBot;
import cn.cloudbot.botmanager.domain.bot.BotStatus;
import cn.cloudbot.botmanager.domain.bot.group.Group;
import cn.cloudbot.botmanager.exceptions.EnumValueException;
import cn.cloudbot.botmanager.exceptions.RobotNotFound;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OuterController {
    @Autowired
    private BotManager botManagerInstance;


    /**
     * 使用 BOT MANAGER
     * lIST 出所有的 bot
     * @return
     */
    @RequestMapping(path = "/robots", method = RequestMethod.GET)
    public @ResponseBody
    Collection<BaseBot> list_bots() {
        return botManagerInstance.listBot();
    }

    /**
     * 使用
     * @param botName
     * @return
     */
    @RequestMapping(path = "/robots/{botName}/status", method = RequestMethod.GET)
    private @ResponseBody
    BotStatus get_status(@PathVariable("botName") String botName) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        return bot.getBotStatus();
    }

    @GetMapping(path = "/robots/{botName}")
    public @ResponseBody BaseBot getBot(@PathVariable("botName") String botName) {
        return botManagerInstance.getBotWithException(botName);
    }

    @RequestMapping(path = "/{botName}/verify_url", method = RequestMethod.GET)
    private @ResponseBody String verify(@PathVariable("botName") String botName) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        return bot.getConnetionUrl();
    }

    @DeleteMapping(path = "/robots/{botName}")
    public ResponseEntity deleteBot(@PathVariable("botName") String botName) {
        boolean exists = botManagerInstance.removeBot(botName);

        HttpStatus status;
        if (exists ) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

//        ResponseEntity entity =
        return new ResponseEntity(status);
    }

    @PostMapping(path = "/robots")
    public ResponseEntity<BaseBot> createBot(@RequestBody BotData botData) {
        BaseBot bot = botManagerInstance.createBot(botData.getBot_type());
        if (bot.getGroup_list() != null) {
            for (Group group:
                    bot.getGroup_list()) {
                bot.addGroup(group);
            }
        }
        return new ResponseEntity<BaseBot>(bot, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/robots/deletegroups")
    public ResponseEntity deleteGroups(@RequestBody BatchGroupCommand deleteGroupCommand) {
        BaseBot bot = botManagerInstance.getBotWithException(deleteGroupCommand.getBot_id());
        for (Group group:
             deleteGroupCommand.getDelete_groups()) {
            bot.removeGroup(group);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/robots/addgroups")
    public ResponseEntity addGroups(@RequestBody BatchGroupCommand addGroupCommand) {
        BaseBot bot = botManagerInstance.getBotWithException(addGroupCommand.getBot_id());

        for (Group group:
             addGroupCommand.getDelete_groups()) {
            bot.addGroup(group);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

//    @DeleteMapping(path = "/robots")
//    public ResponseEntity deleteAll() {
//        BaseBot bot = botManagerInstance.getBotWithException()
//    }



    @ExceptionHandler(RobotNotFound.class)
    public ResponseEntity<Error> robotNotFoundError(RobotNotFound robotNotFound) {
        String robot_name = robotNotFound.getRobot_name();
        return new ResponseEntity<>(new Error(String.format("%s not found", robot_name)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnumValueException.class)
    public ResponseEntity<Error> ValueError(EnumValueException value) {
        return new ResponseEntity<Error>(new Error(String.format("%s not as excepted", value.getValue().toString())), HttpStatus.UNPROCESSABLE_ENTITY);
    }


}

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class BatchGroupCommand {
    private Collection<Group> delete_groups;
    private String bot_id;

    public Collection<Group> getDelete_groups() {
        return delete_groups;
    }

    public void setDelete_groups(Collection<Group> delete_groups) {
        this.delete_groups = delete_groups;
    }

    public String getBot_id() {
        return bot_id;
    }

    public void setBot_id(String bot_id) {
        this.bot_id = bot_id;
    }
}


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class BotData {
//    private String bot_id;
    private String bot_type; // wechat or qq
    private Collection<Group> managed_groups;

    public String getBot_type() {
        return bot_type;
    }

    public void setBot_type(String bot_type) {
        this.bot_type = bot_type;
    }

    public Collection<Group> getManaged_groups() {
        return managed_groups;
    }

    public void setManaged_groups(Collection<Group> managed_groups) {
        this.managed_groups = managed_groups;
    }
}


