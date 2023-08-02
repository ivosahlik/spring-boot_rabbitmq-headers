package cz.ivosahlik.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.ivosahlik.model.Elektro;
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

	// http://localhost:8080/api/v1/test/tv/television
	@GetMapping("/test/tv/{name}")
	public String testTvAPI(@PathVariable("name") String name) throws IOException {
		var p = new Elektro(1L, name);

		var json = mapper.writeValueAsString( p );

		var message = MessageBuilder.withBody(json.getBytes())
				.setHeader("item1", "sony")
				.setHeader("item2", "samsung")
				.build();

		rabbitTemplate.send("Headers-Exchange", "", message);

		return "Success";
	}

	// http://localhost:8080/api/v1/test/mobile/mobile
	@GetMapping("/test/mobile/{name}")
	public String testMobileAPI(@PathVariable("name") String name) throws IOException {
		var p = new Elektro(1L, name);

		var json = mapper.writeValueAsString( p );

		var message = MessageBuilder.withBody(json.getBytes())
				.setHeader("item1", "ios")
				.setHeader("item2", "android")
				.build();

		rabbitTemplate.send("Headers-Exchange", "", message);

		return "Success";
	}

	// http://localhost:8080/api/v1/elektro/elektro
	@GetMapping("/elektro/{name}")
	public String mobileAPI(@PathVariable("name") String name) throws IOException {
		var p = new Elektro(1L, name);

		var json = mapper.writeValueAsString( p );
		
		var message = MessageBuilder.withBody(json.getBytes())
				.setHeader("header", "mobile")
				.build();
		
		rabbitTemplate.send("Headers-Exchange", "", message);
		
		return "Success";
	}

	// http://localhost:8080/api/v1/other/other
	@GetMapping("/other/{name}")
	public String otherAPI(@PathVariable("name") String name) throws IOException {
		var p = new Elektro(1L, name);

		var json = mapper.writeValueAsString( p );

		var message = MessageBuilder.withBody(json.getBytes())
				.setHeader("header", "other")
				.build();

		rabbitTemplate.send("Headers-Exchange", "", message);

		return "Success";
	}
}
