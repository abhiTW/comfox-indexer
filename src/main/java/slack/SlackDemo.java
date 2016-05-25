package slack;

import kafka.KafkaSlackConsumer;
import titan.service.slack.SlackMessageService;

import java.util.Arrays;

public class SlackDemo {
    private KafkaSlackConsumer kafkaSlackConsumer;

    public SlackDemo(KafkaSlackConsumer kafkaSlackConsumer) {
        this.kafkaSlackConsumer = kafkaSlackConsumer;
    }

    public static void main(String[] args) {
        SlackDemo slackDemo = new SlackDemo(new KafkaSlackConsumer(Arrays.asList("slack"), new SlackMessageService()));
        slackDemo.kafkaSlackConsumer.consumeMessages();
    }
}
