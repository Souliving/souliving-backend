FROM gradle:8.6-jdk21-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build


FROM azul/zulu-openjdk-alpine:21
COPY --from=build /home/gradle/src/build/libs/souliving-backend-0.0.1-SNAPSHOT.jar /app/souliving-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xms128m", "-Xmx512m", "-XX:+UseSerialGC", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app/souliving-backend-0.0.1-SNAPSHOT.jar"]
