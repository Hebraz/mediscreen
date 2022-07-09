FROM eclipse-temurin:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} report-api.jar
ENTRYPOINT ["java","-jar","/report-api.jar"]