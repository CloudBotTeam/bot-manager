package cn.cloudbot.botmanager.controller;

import cn.cloudbot.botmanager.domain.bot.BotManager;
import cn.cloudbot.botmanager.domain.bot.BaseBot;
import cn.cloudbot.botmanager.domain.bot.BotStatus;
import cn.cloudbot.botmanager.domain.bot.group.Group;
import cn.cloudbot.botmanager.domain.bot.group.Service;
import cn.cloudbot.botmanager.exceptions.EnumValueException;
import cn.cloudbot.botmanager.exceptions.GroupNotFound;
import cn.cloudbot.botmanager.exceptions.RobotNotFound;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang.NotImplementedException;
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
    @RequestMapping(path = "/robots/{bot_id}/status", method = RequestMethod.GET)
    private @ResponseBody
    BotStatus get_status(@PathVariable("bot_id") Long bot_id) {
        BaseBot bot = botManagerInstance.getBotWithException(bot_id);
        return bot.getBotStatus();
    }

    @GetMapping(path = "/robots/{bot_id}")
    public @ResponseBody BaseBot getBot(@PathVariable("bot_id") Long bot_id) {
        return botManagerInstance.getBotWithException(bot_id);
    }

    @RequestMapping(path = "/robots/{bot_id}/verify_url", method = RequestMethod.GET)
    private @ResponseBody String verify(@PathVariable("bot_id") Long botName) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        return bot.getConnetionUrl();
    }

    @DeleteMapping(path = "/robots/{bot_id}")
    public ResponseEntity deleteBot(@PathVariable("botName") Long botName) {
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
                // set bot_id for group id
                group.setBot_id(bot.getBot_id());
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
    public ResponseEntity addGroups(@RequestBody BatchGroupCommand batchGroupCommand) {
        BaseBot bot = botManagerInstance.getBotWithException(batchGroupCommand.getBot_id());

        for (Group group:
             batchGroupCommand.getAdd_groups()) {

            bot.addGroup(group);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/robots/{botName}/groups")
    private Collection<Group> getRobotsGroups(@PathVariable("botName") Long botName) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        return bot.getGroup_list();
    }

    @GetMapping(path = "/robots/{botName}/groups/{groupId}")
    private Group getRobotGroup(@PathVariable("botName") Long botName, @PathVariable("groupId") String groupId) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        return bot.getGroupByIdWithNotFound(groupId);
    }

    @DeleteMapping(path = "/robots/{botName}/groups/{groupId}/services")
    private void deleteRobotGroupServices(@PathVariable("botName") Long botName,
                                          @PathVariable("groupId") String groupId,
                                          @RequestBody ServList servList) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        Group group = bot.getGroupByIdWithNotFound(groupId);
        group.getServ_list().removeAll(servList.getServices());

    }


    @PostMapping(path = "/robots/{botName}/groups/{groupId}/services")
    private void addRobotGroupServices(@PathVariable("botName") Long botName,
                                          @PathVariable("groupId") String groupId,
                                          @RequestBody ServList servList) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        Group group = bot.getGroupByIdWithNotFound(groupId);
        group.getServ_list().addAll(servList.getServices());
    }

    @DeleteMapping(path = "/robots")
    public void deleteAll() {
        botManagerInstance.deleteAll();
    }



    @ExceptionHandler(RobotNotFound.class)
    public ResponseEntity<Error> robotNotFoundError(RobotNotFound robotNotFound) {
        String robot_name = robotNotFound.getRobot_name();
        return new ResponseEntity<>(new Error(String.format("Robot %s not found", robot_name)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnumValueException.class)
    public ResponseEntity<Error> ValueError(EnumValueException value) {
        return new ResponseEntity<Error>(new Error(String.format("%s not as excepted", value.getValue().toString())), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(GroupNotFound.class)
    public ResponseEntity<Error> groupNotFoundError(GroupNotFound value) {
        return new ResponseEntity<Error>(new Error(String.format("Group %s not found", value.getGroup_name())), HttpStatus.NOT_FOUND);
    }

}

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class BatchGroupCommand {
    private Collection<Group> managed_groups;
    private Long bot_id;

    public Collection<Group> getDelete_groups() {
        return managed_groups;
    }

    public void setDelete_groups(Collection<Group> delete_groups) {
        this.managed_groups = delete_groups;
    }

    public Collection<Group> getAdd_groups() {
        return managed_groups;
    }

    public void setAdd_groups(Collection<Group> add_groups) {
        this.managed_groups = add_groups;
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

    @JsonIgnore
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class ServList {
    private Collection<Service> services;


}
