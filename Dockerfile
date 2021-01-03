FROM openjdk:11
ADD . /src
WORKDIR /src

##TODO: remove
COPY ./cacerts /usr/local/openjdk-11/lib/security/cacerts

RUN ./gradlew --no-daemon \
    clean \
    build \
    bootJar

EXPOSE 9000
HEALTHCHECK --retries=12 --interval=10s CMD curl -s localhost:9000/health || exit 1
COPY /src/build/libs/credit-card-*.jar /usr/local/bin/credit-card.jar
RUN chmod +x /usr/local/bin/credit-card.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/credit-card.jar"]
