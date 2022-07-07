FROM eclipse-temurin:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} person-app.jar
ENTRYPOINT ["java","-jar","/person-app.jar"]