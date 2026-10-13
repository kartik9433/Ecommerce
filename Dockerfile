# Build stage
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /build
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
RUN useradd -r -u 1001 appuser
COPY --from=builder /build/target/EcommerProject-0.0.1-SNAPSHOT.jar app.jar
USER appuser
EXPOSE 9090
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]