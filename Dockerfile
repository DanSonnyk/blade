FROM openjdk:21-jdk

ADD target/blade-app.jar blade-app.jar

ENTRYPOINT ["java", "-jar", "blade-app.jar"]



