FROM java:8
VOLUME /tmp
ADD  build/libs/user-mng-spring-microservice-app-0.0.1-SNAPSHOT.jar user-mng-spring-microservice-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","user-mng-spring-microservice-app-0.0.1-SNAPSHOT.jar"]