package cn.maplewish.botmanager.controller;

import cn.maplewish.botmanager.domain.message.recv_event.event.BotEvent;
import cn.maplewish.botmanager.domain.message.recv_event.meta_event.HeartBeat;
import cn.maplewish.botmanager.exceptions.PayloadCastError;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;
import java.util.logging.Logger;

interface MsgHandler {
    void handle_msg(final Object value);
}

@RestController
public class EventPostController {
    @Autowired
    private Logger logger;

    private static final String HEARTBEAT_FIELD = "meta_event_type";
    private final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public void event(@RequestBody Map<String, Object> payload) {
        logger.info("Receive a request");
        Class target_class = null;
        MsgHandler msgHandler = null;
        if (payload.containsKey(HEARTBEAT_FIELD)) {
            target_class = HeartBeat.class;
            msgHandler = (final Object event) -> {
                HeartBeat obj = (HeartBeat) event;
                handle_heartbeat(obj);
            };
        } else {
            target_class = BotEvent.class;

            msgHandler = (final Object event) -> {
                BotEvent obj = (BotEvent) event;
                handle_event(obj);
            };
//            final BotEvent pojo = mapper.convertValue(payload, BotEvent.class);
//            handle_event(pojo);
        }

        try {
            @SuppressWarnings("unchecked")
            final Object pojo =  mapper.convertValue(payload, target_class);

            msgHandler.handle_msg(pojo);
        } catch (RuntimeException e) {
            throw new PayloadCastError(payload, target_class);
        }


    }

//    如果消息表示的是信息
    private void handle_event(final BotEvent event) {
        logger.info("handle event");
    }
//    如果发送的消息是心跳包
    private void handle_heartbeat(final HeartBeat heartBeatMessage) {
        logger.info("handle heart beat");
    }

    @ExceptionHandler(PayloadCastError.class)
    public ResponseEntity<Error> payloadCastError(PayloadCastError error) {
        return new ResponseEntity<Error>(error.genericError(), HttpStatus.BAD_REQUEST);
    }
}
