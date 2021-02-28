FROM adoptopenjdk/openjdk13-openj9:jdk-13.0.2_8_openj9-0.18.0-alpine-slim
COPY build/libs/bff-0.0.1-SNAPSHOT.jar bff.jar
EXPOSE 9000
CMD ["java", "-jar", "bff.jar"]