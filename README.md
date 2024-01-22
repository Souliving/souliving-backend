Команды

запуск jar
java -Xms128m -Xmx256m -XX:+UseSerialGC -jar souliving-backend-0.0.1-SNAPSHOT.jar

генерация jar без тестов
gradle bootJar

генерация jar с тестами
gradle build

DOCKER

docker build -t souliving-backend .

docker run -dit --memory="512m" --memory-swap="512m" -p 8080:8080 souliving-backend

docker run -dit --memory="512m" --memory-swap="512m" -p 8080:8080 georgedorohov/souliving-backend

docker logs -f $container-id

CREATE TABLE users
(
id SERIAL PRIMARY KEY,
email VARCHAR(64)   NOT NULL UNIQUE,
password VARCHAR(2048) NOT NULL,
role VARCHAR(32)   NOT NULL,
name VARCHAR(64)   NOT NULL,
enabled BOOLEAN NOT NULL DEFAULT FALSE,
create_date TIMESTAMP NOT NULL,
modify_date TIMESTAMP NOT NULL
);

