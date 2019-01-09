package cn.cloudbot.botmanager.controller;

import cn.cloudbot.botmanager.dao.BotEntityService;
import cn.cloudbot.botmanager.domain.bot.BotManager;
import cn.cloudbot.botmanager.domain.message.recv_event.meta_event.HeartBeat;
import cn.cloudbot.botmanager.exceptions.PayloadCastError;
import cn.cloudbot.botmanager.receiver.BotMessageSender;
import cn.cloudbot.common.Message.BotMessage.RobotSendMessage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

interface MsgHandler {
    void handle_msg(final Object value);
}

@CrossOrigin
@RestController
public class EventPostController {
    @Autowired
    private BotEntityService botEntityService;

    @Autowired
    private Logger logger;

    @Autowired
    private BotMessageSender sender;

    @Autowired
    private BotManager botManager;

    private static final String HEARTBEAT_FIELD = "meta_event_type";
    private final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static AtomicInteger info_cnt = new AtomicInteger(0);

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void event(@RequestBody Map<String, Object> payload) {
//        extract request ip and match the ip in robot manager
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getRemoteAddr();
        Integer port = request.getRemotePort();

        logger.info("Receive a request(id): " + info_cnt.incrementAndGet() + " from ip : " + ip + " and port " + port.toString());

//        parse data
        Class target_class = null;
        MsgHandler msgHandler = null;
        if (payload.containsKey(HEARTBEAT_FIELD)) {
            target_class = HeartBeat.class;
            msgHandler = (final Object event) -> {
                HeartBeat obj = (HeartBeat) event;
                handle_heartbeat(obj);
            };
        } else {
            target_class = RobotSendMessage.class;

            msgHandler = (final Object event) -> {
                RobotSendMessage obj = (RobotSendMessage) event;
                handle_event(obj, ip);

            };
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
    private void handle_event(final RobotSendMessage event, final String from_ip) {
//        logger.info("handle event");
//        StringBuilder builder = new StringBuilder();
//        for (RobotSendMessageSegment segment:
//             event.getData()) {
//            builder.append(segment.toString());
//        }
        logger.info("send message: " + event.toString() + " with detail ");

        sender.sendData().send(MessageBuilder.withPayload(event).build());
    }

//    如果发送的消息是心跳包
    private void handle_heartbeat(final HeartBeat heartBeatMessage) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        botManager.handleHeartBeat(request.getRemoteAddr(), heartBeatMessage);
    }

    @ExceptionHandler(PayloadCastError.class)
    public ResponseEntity<Error> payloadCastError(PayloadCastError error) {
        logger.info(new JSONObject(error.getValue_map()).toJSONString());
        return new ResponseEntity<Error>(error.genericError(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/test/ip")
    public void iptest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getRemoteAddr();
        logger.info("Test Get ip " + ip);
    }
}
