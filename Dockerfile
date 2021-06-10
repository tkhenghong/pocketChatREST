# https://spring.io/guides/gs/spring-boot-Docker/
# https://dzone.com/articles/how-to-package-a-spring-boot-app-as-a-docker-container
# https://www.youtube.com/watch?v=UwmrvkC8cM4
# https://www.baeldung.com/dockerizing-spring-boot-application
# http://paulbakker.io/java/docker-gradle-multistage/

# This is important to build the whole project first using Gradle first when you're running in Docker Hub.
# http://paulbakker.io/java/docker-gradle-multistage/


# Using the solution of this StackOverflow:
# https://stackoverflow.com/questions/61108021/gradle-and-docker-how-to-run-a-gradle-build-within-docker-container
# https://docs.docker.com/develop/develop-images/multistage-build/

# Issues when trying to build and run the Docker image:
# Gradle build failed: Main class name has not been configured and it could not be resolved:
# https://stackoverflow.com/questions/56861256/gradle-build-failed-main-class-name-has-not-been-configured-and-it-could-not-be/56882464

# temp container to build using gradle
FROM gradle:jdk16 as TEMP_BUILD_IMAGE

MAINTAINER Teoh Kheng Hong tkhenghong@gmail.com

ENV APP_HOME=/tmp/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle clean build --stacktrace || return 0
COPY . .
RUN gradle clean build --stacktrace

# actual container
FROM openjdk:15.0.1

MAINTAINER Teoh Kheng Hong tkhenghong@gmail.com

ENV ARTIFACT_NAME=pocketchat-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/tmp/app/

USER root
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

# What exactly does “-Djava.security.egd=file:/dev/./urandom” do when containerizing a Spring Boot application:
# https://stackoverflow.com/questions/58853372/what-exactly-does-djava-security-egd-file-dev-urandom-do-when-containerizi

# Tell Docker to let the app use port number 8888 within the Docker container. (Not outside)
EXPOSE 8888

ENTRYPOINT exec java "-Djava.security.egd=file:/dev/./urandom" -jar $ARTIFACT_NAME
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "pocketchat-0.0.1-SNAPSHOT.jar"]


# Build this Spring Boot project:
# gradle build
# OR
# gradlew build

# Login into Docker in your CLI:
# docker login

# List all Docker images
# docker ps -a

# Build Docker Image: (If not giving -t "Name", it will give you a random funny name) (Name must be all lowercases)
# docker build -t *NAME OF THE APP* . (FORMULA)

# docker build -t tkhenghong/pocketchat .

# Run Docker normally:
# docker run -p 8080:8080 --name=nameofthedocker -t *NAME OF THE APP* (FORMULA)

# docker run -p 8888:8888 --name=pocketChat -t tkhenghong/pocketchat

# Run Docker with Spring Profile:
# docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8888:8888  --name=pocketChat -t tkhenghong/pocketchat

# Push Docker image to Docker hub:
# http://blog.shippable.com/build-a-docker-image-and-push-it-to-docker-hub
# sudo docker push tkhenghong/pocketchat:latest

# View all Docker images:
# sudo docker images

# Remove all unused Docker images:
# docker system prune -a

# Remove all current Docker images, containers and volumes
# sudo docker rm -f $(sudo docker ps -aq)

# Enter the CLI/terminal of any container (with name)
# docker exec -it *CONTAINER_NAME* bash

# docker exec -it mongodb bash
# docker exec -it pocketChat bash
