#Base image
FROM eclipse-temurin:21-jdk-jammy

#Working dir
WORKDIR /app

#Copying files
COPY target/EcommerProject-0.0.1-SNAPSHOT.jar app.jar

#Expose Port
EXPOSE ${SERVER_PORT}

#Java jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]


