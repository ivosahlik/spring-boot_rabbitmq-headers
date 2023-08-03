package cz.ivosahlik.controller;

import cz.ivosahlik.model.Picture;
import cz.ivosahlik.producer.MyPictureProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MyPictureImageController {

    final MyPictureProducer myPictureProducer;
    private final List<String> SOURCES = List.of("mobile", "web");
    private final List<String> TYPES = List.of("jpg", "png", "svg");


    // http://localhost:8080/api/v1/test/mypicture
    @GetMapping("/test/mypicture")
    public void testMypictureAPI() throws IOException {
        for (int i = 0; i < 1; i++) {
            var picture = new Picture();
            picture.setName("Picture " + i);
            picture.setSize(ThreadLocalRandom.current().nextLong(1000, 10000));
            picture.setSource(SOURCES.get(i % TYPES.size()));
            picture.setType(TYPES.get(i % TYPES.size()));
            myPictureProducer.sendMessage(picture);
        }
    }
}
