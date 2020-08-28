# https://spring.io/guides/gs/spring-boot-Docker/
# https://dzone.com/articles/how-to-package-a-spring-boot-app-as-a-docker-container
# https://www.youtube.com/watch?v=UwmrvkC8cM4
# https://www.baeldung.com/dockerizing-spring-boot-application
# http://paulbakker.io/java/docker-gradle-multistage/

# This is important to build the whole project first using Gradle first when you're running in Docker Hub.
# http://paulbakker.io/java/docker-gradle-multistage/
FROM gradle:jdk14 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

#CMD echo "Command 2: Built JAR file should be below:"
#CMD ls /home/gradle/src/build/libs
#CMD echo "Command 2 END"

FROM openjdk:latest

MAINTAINER Teoh Kheng Hong tkhenghong@gmail.com

#COPY --from=builder /home/gradle/src/build/libs/pocketchat-0.0.1-SNAPSHOT.jar /tmp/

#CMD echo "Command 3: Copied JAR file should be below:"
#CMD ls /tmp/
#CMD echo "Command 3 END"

#RUN ls
# VOLUME /tmp is important for you if your application need to create a file in the filesystem in the container(File upload/download)
#VOLUME /tmp
#WORKDIR /tmp

#VOLUME /app
WORKDIR /home/gradle/src
#RUN ls
RUN mkdir -p /app/

ADD build/libs/pocketchat-0.0.1-SNAPSHOT.jar /app/app.jar
#ADD /tmp/pocketchat-0.0.1-SNAPSHOT.jar pocketchat.jar

COPY src/main/resources $HOME/src/main/resources

#RUN echo "Command 4: Items in /home/gradle/src/main/resources directory:"
#RUN ls /home/gradle/src/main/resources
#RUN echo "Command 4 END"

#RUN echo "Command 5: Items in $HOME/src/main/resources directory:"
#RUN ls $HOME/src/main/resources
#RUN echo "Command 5 END"

# What exactly does “-Djava.security.egd=file:/dev/./urandom” do when containerizing a Spring Boot application:
# https://stackoverflow.com/questions/58853372/what-exactly-does-djava-security-egd-file-dev-urandom-do-when-containerizi

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pocketchat.jar"]

# Tell Docker to let the app use port number 8888 within the Docker container. (Not outside)
EXPOSE 8888 27107 5672 15672

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
