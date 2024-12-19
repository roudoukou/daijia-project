
#停止并删除容器：
docker-compose down
#删除 MySQL 数据卷（这将清除所有数据）：
docker volume prune
#重新启动容器：
docker-compose up -d
