package cz.ivosahlik.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ivosahlik.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQConsumer {

	private final ObjectMapper mapper;

//	@RabbitListener(queues = "Mobile")
//	public void getMessage(byte[] payload) {
//		messageNotification(new String(payload, StandardCharsets.UTF_8));
//	}

	@RabbitListener(queues = "Mobile")
	public void getMessage(Message message) {
		var headers = message.getMessageProperties().getHeaders();
		var header = headers.get("header").toString();

		log.info(headers.toString());

		if (header.equals("mobile")) {
			messageNotification(new String(message.getBody(), StandardCharsets.UTF_8));
		} else {
			log.info("Consumer: Message header not exists!!!");
		}
	}

	public void messageNotification(String payload) {
		try {
			var readValue = mapper.readValue(payload, mapper.getTypeFactory().constructType(Person.class));
			log.info("Consumer: {}", readValue.toString());
		} catch (Exception exception) {
			log.error("Failed to process Person update.", exception);
		}
	}
}
