package cz.ivosahlik.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import cz.ivosahlik.model.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPictureImageConsumer {

    private final ObjectMapper objectMapper;

    // Consumer Exception without DLX
//    @RabbitListener(queues = "x.mypicture.image")
//    public void listenWithoutDLX(String message) throws IOException, AmqpRejectAndDontRequeueException {
//        var picture = objectMapper.readValue(message, Picture.class);
//
//        if (picture.getSize() > 9000) {
//			throw new AmqpRejectAndDontRequeueException("Picture size too large : " + picture);
//        }
//
//        log.info("On image : {}", picture);
////        channel.basicAck(tag, false);
//    }

    // Handle Consumer Exception with DLX (Solution One)
    // Exchange – fanout
    @RabbitListener(queues = "x.mypicture.image")
    public void listen(String message,
                       Channel channel,
                       @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, AmqpRejectAndDontRequeueException {
        var picture = objectMapper.readValue(message, Picture.class);
        if (picture.getSize() > 9000) {
            log.info("DLX -> Picture size: {}", picture.getSize());
            channel.basicReject(tag, false);
            return;
        }

        log.info("On image : {}", picture);
        channel.basicAck(tag, false);
    }
}