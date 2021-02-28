FROM adoptopenjdk/openjdk13-openj9:jdk-13.0.2_8_openj9-0.18.0-alpine-slim
COPY build/libs/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar
EXPOSE 8080
CMD ["java", "-jar", "customer-service.jar"]