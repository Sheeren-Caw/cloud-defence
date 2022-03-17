FROM openjdk:8
EXPOSE 8080
ADD target/clouddefence-0.1.jar clouddefence-0.1.jar
ENTRYPOINT ["java", "-jar", "/clouddefence-0.1.jar"]