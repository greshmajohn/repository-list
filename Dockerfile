FROM openjdk:17
VOLUME /tmp/app
EXPOSE 8087
WORKDIR /home

COPY target/repositories-0.0.1-SNAPSHOT.jar /app/build/libs/repository-list.jar
ENTRYPOINT ["java","-jar","/app/build/libs/repository-list.jar"]