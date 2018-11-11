## Very simple kafka consumer application (without spring framework).

**To run it:**

1. Install Scala https://www.scala-lang.org/news/2.12.0/
2. Download and unzip kafka 2.12 binaries https://kafka.apache.org/downloads
3. Install Java JDK 8+
4. Run Zookeeper: 
   ```sh
   [Kafka_dir]\bin\windows> .\zookeeper-server-start.bat [Kafka_dir]\config\zookeeper.properties
    ```
5. Run broker:
   ```sh
   [Kafka_dir]\bin\windows>.\kafka-server-start.bat [Kafka_dir]\config\server.properties
   ```
6. Create 2 topics with 3 partitions each and replication factor of 1:
   ```sh
   [Kafka_dir]\bin\windows>.\kafka-topics.bat --zookeeper localhost:2181 --create --topic my-topic --partitions 3 --replication-factor 1
   [Kafka_dir]\bin\windows>.\kafka-topics.bat --zookeeper localhost:2181 --create --topic my-other-topic --partitions 3 --replication-factor 1
   ```
7. Run Application
8. To produce some messages use the .\kafka-producer-perf-test.bat script
   ```sh
   .\kafka-producer-perf-test.bat --topic my-topic --num-records 50 --record-size 1 --t
   hroughput 10 --producer-props bootstrap.servers=localhost:9092 key.serializer=org.apache.kafka.common.serializer.StringS
   erializer value.serializer=org.apache.kafka.common.serializer.StringSerializer
   
   .\kafka-producer-perf-test.bat --topic my-other-topic --num-records 50 --record-size 1 --t
   hroughput 10 --producer-props bootstrap.servers=localhost:9092 key.serializer=org.apache.kafka.common.serializer.StringS
   erializer value.serializer=org.apache.kafka.common.serializer.StringSerializer
   ```
9. Verify that if you will execute consumeMessagesSubscribe() then messages from all partitions will be read (even if partition will be added dynamically in time when application is already running).

    Verify that if you will execute consumeMessagesAssign() then always messages from fixed partitions will be read
###### Note :exclamation:
If you will have some issues while running zookeeper or broker.
Then close all consoles and clear tmp directory in which zookeeper and broker keeps the logs (for example C:\tmp)