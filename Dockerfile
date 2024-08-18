#FROM eclipse-temurin:22-jre-jammy AS builder
#WORKDIR workspace
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} api-reservations.jar
#RUN java -Djarmode=layertools -jar api-reservations.jar extract
#
#FROM eclipse-temurin:22-jre-jammy
#WORKDIR workspace
#COPY --from=builder workspace/dependencies/ ./
#COPY --from=builder workspace/spring-boot-loader/ ./
#COPY --from=builder workspace/snapshot-dependencies/ ./
#COPY --from=builder workspace/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher", "--spring.config.location=classpath:/application-docker.yml"]

FROM eclipse-temurin:22-jre-jammy
WORKDIR workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-reservations.jar
ENTRYPOINT ["java", "-jar", "api-reservations.jar", "--spring.config.location=classpath:/application-docker.yml"]

