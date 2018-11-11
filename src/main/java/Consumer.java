import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Consumer {

    /**
     * Consumes messages using assign method.
     * Messages will be consumed only from specified topics and partitions.
     */
    public void consumeMessagesAssign() {
        Properties properties = getProperties();
        KafkaConsumer consumer = new KafkaConsumer(properties);
        List<TopicPartition> partitions = getPartitions();
        consumer.assign(partitions);
        poll(consumer);
    }

    /**
     * Consumes messages using subscribe method.
     * Messages will be consumed from all partitions from specified topic
     * (even if they will be added dynamically later).
     */
    public void consumeMessagesSubscribe() {
        Properties properties = getProperties();
        KafkaConsumer consumer = new KafkaConsumer(properties);
        List<String> topics = getTopics();
        consumer.subscribe(topics);
        poll(consumer);
    }

    private List<String> getTopics() {
        return Arrays.asList("my-topic", "my-other-topic");
    }

    private void poll(KafkaConsumer consumer) {
        try {
            tryPoll(consumer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            consumer.close();
        }
    }

    private void tryPoll(KafkaConsumer consumer) {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value()));
            }
        }
    }

    private Properties getProperties() {
        //http://kafka.apache.org/documentation.html#consumerconfigs
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        return properties;
    }

    private List<TopicPartition> getPartitions() {
        return Arrays.asList(new TopicPartition("my-topic", 0),
                new TopicPartition("my-other-topic", 2));
    }
}
