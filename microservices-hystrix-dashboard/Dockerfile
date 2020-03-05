FROM openjdk:8-jre-alpine
MAINTAINER giuseppe.calsolaro87@gmail.com
EXPOSE 7979
ADD target/*.jar /app.jar
RUN touch /app.jar
ADD wait-for-it.sh /wait-for-it.sh
RUN chmod 777 /wait-for-it.sh
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]