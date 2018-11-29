package cn.maplewish.botmanager.controller;

import cn.maplewish.botmanager.domain.message.recv_event.event.BotEvent;
import cn.maplewish.botmanager.domain.message.recv_event.meta_event.HeartBeat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

@RestController
public class EventPostController {

    private static final String HEARTBEAT_FIELD = "meta_event_type";
    private final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public void event(@RequestBody Map<String, Object> payload) {
        if (payload.containsKey(HEARTBEAT_FIELD)) {
            final HeartBeat pojo = mapper.convertValue(payload, HeartBeat.class);
            handle_heartbeat(pojo);
        } else {
            final BotEvent pojo = mapper.convertValue(payload, BotEvent.class);
            handle_event(pojo);
        }
    }

//    如果消息表示的是信息
    private void handle_event(final BotEvent event) {
        throw new NotImplementedException();
    }
//    如果发送的消息是心跳包
    private void handle_heartbeat(final HeartBeat heartBeatMessage) {
        throw new NotImplementedException();
    }
}
