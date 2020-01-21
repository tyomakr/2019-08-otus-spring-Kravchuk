FROM maven:3-jdk-8-openj9 AS mvn

ENV PROJECT_DIR=/opt/backend
RUN mkdir -p $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
WORKDIR $PROJECT_DIR
RUN mvn dependency:resolve

COPY ./backend/src/ $PROJECT_DIR/src
RUN mvn install


FROM openjdk:8-alpine

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=mvn opt/backend/target/spring17-docker* $PROJECT_DIR/

EXPOSE 8888

CMD ["java", "-jar", "/opt/project/spring17-docker-0.1.jar"]
