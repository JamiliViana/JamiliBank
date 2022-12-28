FROM openjdk
WORKDIR /usr/app

ARG JAR_FILE=target/jamilibank-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
