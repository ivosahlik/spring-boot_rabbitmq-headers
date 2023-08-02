package cz.ivosahlik.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ivosahlik.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RabbitMqController {

	final RabbitTemplate rabbitTemplate;
	final ObjectMapper mapper;

	@GetMapping("/mobile/{name}")
	public String mobileAPI(@PathVariable("name") String name) throws IOException {
		var p = new Person(1L, name);

		var json = mapper.writeValueAsString( p );
		
		var message = MessageBuilder.withBody(json.getBytes())
				.setHeader("header", "mobile")
				.build();
		
		rabbitTemplate.send("Headers-Exchange", "", message);
		
		return "Success";
	}

	@GetMapping("/other/{name}")
	public String otherAPI(@PathVariable("name") String name) throws IOException {
		var p = new Person(1L, name);

		var json = mapper.writeValueAsString( p );

		var message = MessageBuilder.withBody(json.getBytes())
				.setHeader("header", "other")
				.build();

		rabbitTemplate.send("Headers-Exchange", "", message);

		return "Success";
	}
}
