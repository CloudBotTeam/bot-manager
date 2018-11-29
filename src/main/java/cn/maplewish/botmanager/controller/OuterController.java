package cn.maplewish.botmanager.controller;

import cn.maplewish.botmanager.beans.BotManager;
import cn.maplewish.botmanager.domain.message.recv_event.meta_event.Status;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

@RestController
public class OuterController {
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public Map<String, BotManager> list_bots() {
        throw new NotImplementedException();
    }

    @RequestMapping(path = "/status", method = RequestMethod.GET)
    private Status get_status() {
        throw new NotImplementedException();
    }

    @RequestMapping(path = "/verify_url", method = RequestMethod.GET)
    private String verify() {
        throw new NotImplementedException();
    }

}
