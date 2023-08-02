package cz.ivosahlik.controller;

import cz.ivosahlik.model.RabbitmqQueue;
import cz.ivosahlik.service.RabbitmqClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RabbitmqWebController {

    private final RabbitmqClient client;

    @GetMapping("/test")
    public void testTvAPI() throws IOException {
        try {
            var dirtyQueues = client.getAllQueues()
                    .stream()
                    .filter(RabbitmqQueue::isDirty)
                    .toList();
            dirtyQueues.forEach(q -> log.info("Queue {} has {} unprocessed messages", q.getName(), q.getMessages()));
        } catch (Exception e) {
            log.warn("Cannot sweep queue : {}", e.getMessage());
        }
    }
}
