FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/test-performance-batch-insert-1.0.0-SNAPSHOT.jar /app.jar
RUN ls -lha
ENTRYPOINT ["java","-jar","/app.jar"]