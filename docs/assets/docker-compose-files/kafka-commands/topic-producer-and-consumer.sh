#!/bin/zsh

#to start kafka-consumer console for topic 'input_item_topic'.
docker exec -it spring-camel-route-kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic input_item_topic

#to start kafka-producer console for topic 'input_item_topic'.
docker exec -it spring-camel-route-kafka kafka-console-producer.sh --bootstrap-server localhost:9092 --topic input_item_topic
