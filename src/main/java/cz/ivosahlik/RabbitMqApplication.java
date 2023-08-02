package cz.ivosahlik;

import cz.ivosahlik.model.Picture;
import cz.ivosahlik.producer.MyPictureProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class RabbitMqApplication implements CommandLineRunner {

	private final List<String> SOURCES = List.of("mobile", "web");
	private final List<String> TYPES = List.of("jpg", "png", "svg");

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqApplication.class, args);
	}

	@Autowired
	private MyPictureProducer myPictureProducer;

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 1; i++) {
			var picture = new Picture();
			picture.setName("Picture " + i);
			picture.setSize(ThreadLocalRandom.current().nextLong(9000, 10000));
			picture.setSource(SOURCES.get(i % TYPES.size()));
			picture.setType(TYPES.get(i % TYPES.size()));
			myPictureProducer.sendMessage(picture);
		}
	}

}
