package kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = "socket-chat", groupId = "kafka-sandbox")
    public void listen(String message) {
        System.out.println("sending via kafka listener...");
        template.convertAndSend("/topic/group", message);
    }

}
