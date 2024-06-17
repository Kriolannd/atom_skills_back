FROM mirror.gcr.io/eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN sed -i 's/\r$//' ./mvnw && chmod +x ./mvnw && ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]