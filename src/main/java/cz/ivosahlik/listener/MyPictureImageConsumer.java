package cz.ivosahlik.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import cz.ivosahlik.model.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class MyPictureImageConsumer {

    private final ObjectMapper objectMapper;

    // Consumer Exception without DLX
    @RabbitListener(queues = "x.mypicture.image")
    public void listen(String message,
                       Channel channel,
                       @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        var picture = objectMapper.readValue(message, Picture.class);

        if (picture.getSize() > 9000) {
            channel.basicReject(tag, false);
            return;
//			throw new AmqpRejectAndDontRequeueException("Picture size too large : " + picture);
        }

        log.info("On image : {}", picture);
        channel.basicAck(tag, false);
    }

}