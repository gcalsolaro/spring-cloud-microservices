FROM openjdk:8-jre-alpine
MAINTAINER giuseppe.calsolaro87@gmail.com
EXPOSE 8888
ADD target/*.jar /app.jar
RUN touch /app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=native", "-jar", "/app.jar"]