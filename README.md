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

