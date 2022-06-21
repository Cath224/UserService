FROM openjdk:17-alpine
RUN addgroup -S kate && adduser -S kate -G kate
USER kate:kate
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9004
CMD ["java", "-Xmx512m","-jar", "app.jar"]