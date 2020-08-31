# https://spring.io/guides/gs/spring-boot-Docker/
# https://dzone.com/articles/how-to-package-a-spring-boot-app-as-a-docker-container
# https://www.youtube.com/watch?v=UwmrvkC8cM4
# https://www.baeldung.com/dockerizing-spring-boot-application
# http://paulbakker.io/java/docker-gradle-multistage/

# This is important to build the whole project first using Gradle first when you're running in Docker Hub.
# http://paulbakker.io/java/docker-gradle-multistage/


# Using the solution of this StackOverflow:
# https://stackoverflow.com/questions/61108021/gradle-and-docker-how-to-run-a-gradle-build-within-docker-container

# temp container to build using gradle
FROM gradle:jdk14 as TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle build || return 0
COPY . .
RUN gradle clean build

# actual container
FROM openjdk:latest

MAINTAINER Teoh Kheng Hong tkhenghong@gmail.com

ENV ARTIFACT_NAME=pocketchat-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

WORKDIR /app

# What exactly does “-Djava.security.egd=file:/dev/./urandom” do when containerizing a Spring Boot application:
# https://stackoverflow.com/questions/58853372/what-exactly-does-djava-security-egd-file-dev-urandom-do-when-containerizi

# Tell Docker to let the app use port number 8888 within the Docker container. (Not outside)
EXPOSE 8888 27107 5672 15672

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "$ARTIFACT_NAME"]


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
