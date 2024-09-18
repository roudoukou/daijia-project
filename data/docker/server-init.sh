#!/bin/bash

RABBITMQ_CONTAINER_NAME="rabbitmq-daijia"
CURRENT_DIR=$(pwd)

# 判断当前的端口是否占用
# 5672 15672

mkdir -p /opt/software

docker-compose -f rabbitmq-compose.yaml up -d

wget -P /opt/software https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.12.0/rabbitmq_delayed_message_exchange-3.12.0.ez

docker cp /opt/software/rabbitmq_delayed_message_exchange-3.12.0.ez ${RABBITMQ_CONTAINER_NAME}:/plugins

docker exec -it ${RABBITMQ_CONTAINER_NAME} /bin/bash -c "rabbitmq-plugins enable rabbitmq_delayed_message_exchange"

docker-compose -f rabbitmq-compose.yaml restart


