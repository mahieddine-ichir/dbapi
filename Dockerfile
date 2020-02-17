FROM openjdk:12

ADD target/dbapi-0.0.1-SNAPSHOT.jar /usr/src/app.jar

ENTRYPOINT ["java", "-jar", "/usr/src/app.jar"]
