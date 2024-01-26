FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:21-jdk-slim-buster
COPY --from=build /home/gradle/src/build/libs/souliving-backend-0.0.1-SNAPSHOT.jar /app/
RUN bash -c 'touch /app/souliving-backend-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java", "-Xms128m", "-Xmx512m", "-XX:+UseSerialGC", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app/souliving-backend-0.0.1-SNAPSHOT.jar"]
