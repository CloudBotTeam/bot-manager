package cn.maplewish.botmanager.controller;

import cn.maplewish.botmanager.domain.message.recv_event.BotEvent;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventPostController {


    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public void event(@RequestBody BotEvent botEvent) {

    }
}
