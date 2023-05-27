FROM amazoncorretto:17-al2-jdk
WORKDIR /app
COPY /target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]