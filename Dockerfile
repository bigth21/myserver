FROM openjdk:17-jre
COPY build/libs/*.jar myserver.jar
ENTRYPOINT ["java", "-jar", "myserver.jar"]