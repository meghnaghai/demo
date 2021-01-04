FROM openjdk:11 AS build
ADD . /src
WORKDIR /src

RUN ./gradlew --no-daemon \
    clean \
    build \
    bootJar

EXPOSE 9000
HEALTHCHECK --retries=12 --interval=10s CMD curl -s localhost:9000/health || exit 1
RUN ls -lart /src/build/libs
RUN cp /src/build/libs/credit-card-1.0-SNAPSHOT.jar /usr/local/bin/credit-card.jar

RUN chmod +x /usr/local/bin/credit-card.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/credit-card.jar"]
