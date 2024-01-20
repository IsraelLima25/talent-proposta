FROM openjdk:11
WORKDIR /app
COPY target/proposta-0.0.1-SNAPSHOT.jar /app/proposta-app.jar
ENTRYPOINT ["java", "-jar", "proposta-app.jar"]