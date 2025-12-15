FROM eclipse-temurin:17-jdk

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} paymentservice.jar

ENTRYPOINT ["java", "-jar", "/paymentservice.jar"]

EXPOSE 8081

