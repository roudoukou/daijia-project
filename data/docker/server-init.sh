#!/bin/bash

RABBITMQ_CONTAINER_NAME="rabbitmq-daijia"
CURRENT_DIR=$(pwd)

# 判断当前的端口是否占用
# 5672 15672

mkdir -p /data/{rabbitmq,mysql,nacos,redis}

mkdir -p /opt/software


# mysql配置
touch /data/mysql/config/my.cnf

cat <<EOF > /data/mysql/config/my.cnf
[mysqld]
user=mysql
default-storage-engine=INNODB
character-set-server=utf8
character-set-client-handshake=FALSE
collation-server=utf8mb4_general_ci
init_connect='SET NAMES utf8'
max_connections=1000
[client]
default-character-set=utf8mb4
[mysql]
default-character-set=utf8mb4
EOF

# rabbitmq配置

docker-compose -f docker-compose.yaml up -d

wget -P /opt/software https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.12.0/rabbitmq_delayed_message_exchange-3.12.0.ez

docker cp /opt/software/rabbitmq_delayed_message_exchange-3.12.0.ez ${RABBITMQ_CONTAINER_NAME}:/plugins

docker exec -it ${RABBITMQ_CONTAINER_NAME} /bin/bash -c "rabbitmq-plugins enable rabbitmq_delayed_message_exchange"

docker-compose -f docker-compose.yaml restart



