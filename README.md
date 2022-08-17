# event-driven-architecture-twitter-streaming-service-to-kafka
This is a demo microservice for streaming twitter to apache kafka based on event driven architecture.

Replace the twitter4j.properties files with your credentials by setting up in twitter developer portal.
Replace bearer token of twitter in TwitterConfig.
For windows in .env file use
COMPOSE_PATH_SEPERATOR=;
COMPOSE_FILE=common.yml;kafka_cluster.yml;services.yml

For unix in .env file use
COMPOSE_PATH_SEPERATOR=:
COMPOSE_FILE=common.yml:kafka_cluster.yml:services.yml

run with docker compose up
stop with docker compose down
to check kafka status use kafkacat
docker run -it --network=host edenhill/kcat:1.7.1 -b localhost:19092  -L
to check the data in topic use kafkacat as
docker run -it --network=host edenhill/kcat:1.7.1 -b localhost:19092  -C -t twitter-topic

