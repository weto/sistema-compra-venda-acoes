FROM openjdk:8-jdk-alpine

COPY . /tmp

RUN cd /tmp && ./mvnw install -DskipTests

RUN mkdir -p /app

RUN cp /tmp/target/*.jar /app/app.jar

RUN rm -rf /tmp/*

WORKDIR /app

VOLUME /app

RUN sh -c 'touch app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]