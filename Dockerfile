FROM bellsoft/liberica-openjdk-alpine:21-37-x86_64
ARG JAR_FILE=build/libs/souliving-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} souliving-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xms128m", "-Xmx512m", "-XX:+UseSerialGC", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom", "-jar","souliving-backend-0.0.1-SNAPSHOT.jar"]
