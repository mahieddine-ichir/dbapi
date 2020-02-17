FROM openjdk:12

ADD target/mariadb-client-0.0.1-SNAPSHOT.jar /usr/src/app.jar

ENTRYPOINT ["java", "-jar", "/usr/src/app.jar"]
