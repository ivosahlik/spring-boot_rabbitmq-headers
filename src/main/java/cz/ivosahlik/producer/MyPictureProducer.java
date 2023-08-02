package cz.ivosahlik.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ivosahlik.model.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class MyPictureProducer {

	private final RabbitTemplate rabbitTemplate;

	private final ObjectMapper objectMapper;
	
	public void sendMessage(Picture picture) throws IOException {
		var json = objectMapper.writeValueAsString(picture);
		rabbitTemplate.convertAndSend("x.mypicture", picture.getType(), json);
	}
	
}
