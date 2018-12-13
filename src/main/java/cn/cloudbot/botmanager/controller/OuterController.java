package cn.cloudbot.botmanager.controller;

import cn.cloudbot.botmanager.config.beans.BotManager;
import cn.cloudbot.botmanager.domain.bot.BaseBot;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.Status;
import cn.cloudbot.botmanager.exceptions.RobotNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("/robots")
public class OuterController {
    @Autowired
    private BotManager botManagerInstance;


    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public @ResponseBody Map<String, BaseBot> list_bots() {
        return botManagerInstance.listBot();
    }

    @RequestMapping(path = "/{botName}/status", method = RequestMethod.GET)
    private @ResponseBody
    Status get_status(@PathVariable("botName") String botName) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        return bot.checkBotStatus();
    }

    @RequestMapping(path = "/{botName}/verify_url", method = RequestMethod.GET)
    private @ResponseBody String verify(@PathVariable("botName") String botName) {
        BaseBot bot = botManagerInstance.getBotWithException(botName);
        return bot.getConnetionUrl();
    }

    @ExceptionHandler(RobotNotFound.class)
    public ResponseEntity<Error> robotNotFoundError(RobotNotFound robotNotFound) {
        String robot_name = robotNotFound.getRobot_name();
        return new ResponseEntity<>(new Error(String.format("%s not found", robot_name)), HttpStatus.NOT_FOUND);
    }
}


