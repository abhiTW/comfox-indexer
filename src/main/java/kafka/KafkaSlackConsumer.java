package kafka;

import com.google.gson.Gson;
import model.slack.SlackMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import titan.service.slack.SlackMessageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class KafkaSlackConsumer {
    private KafkaConsumer<String, String> kafkaConsumer;
    private SlackMessageService slackMessageService;

    public KafkaSlackConsumer(List<String> topics, SlackMessageService slackMessageService) {
        this.slackMessageService = slackMessageService;
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka-docker1-745a8732.d5e66301.svc.dockerapp.io:9092");
        props.put("group.id", "Slack-consumer");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        kafkaConsumer = new KafkaConsumer<String, String>(props);
        kafkaConsumer.subscribe(topics);
    }

    public void consumeMessages() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(200);
                for (ConsumerRecord<String, String> record : records) {
                    String jsonData = record.value();
                    SlackMessage slackMessage = unmarshallSlackJson(jsonData);
                    slackMessageService.createSlackMessageNode(slackMessage);
                }
            }
        } finally {
            kafkaConsumer.close();
        }
    }

    private SlackMessage unmarshallSlackJson(String jsonData) {
        Gson gson = new Gson();
        SlackMessage slackMessage = gson.fromJson(jsonData, SlackMessage.class);
        return slackMessage;
    }
}
