#!/bin/zsh
#create kafka topic(s)
docker exec spring-camel-route-kafka kafka-topics.sh --create --topic input_item_topic --partitions 3 --replication-factor 1 --bootstrap-server localhost:9092
docker exec spring-camel-route-kafka kafka-topics.sh --create --topic error_topic --partitions 3 --replication-factor 1 --bootstrap-server localhost:9092

#list kafka topic(s)
docker exec spring-camel-route-kafka kafka-topics.sh --list --bootstrap-server localhost:9092

#to list kafka broker API details
docker exec spring-camel-route-kafka kafka-broker-api-versions.sh --bootstrap-server localhost:9092

#to describe already created kafka topics
docker exec spring-camel-route-kafka kafka-configs.sh --describe --all --bootstrap-server localhost:9092 --topic input_item_topic
docker exec spring-camel-route-kafka kafka-configs.sh --describe --all --bootstrap-server localhost:9092 --topic error_topic

#to delete already created kafka topics
docker exec spring-camel-route-kafka kafka-topics.sh --delete --topic input_item_topic --bootstrap-server localhost:9092
