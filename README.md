# Credit Card
The given repo contains microservice to add a new credit card and view all credit cards created so far.

## Run Application

To run application run below command on local:
```./gradlew bootRun```

Check service is up using below endpoint.
```http://localhost:9000/actuator/health```

The swagger URL can be seen at 
```http://localhost:9000/swagger-ui.html```

## Testcases

* In order to run test cases, run below command:
```./gradlew test```

* To run single test case, use below command:
```./gradlew test --tests <filename>.<testcase>```

* To see covergae, run below command:
```./gradlew jacocoTestReport```

## Docker
* To build image using docker file, use below command from root of project:
```docker build -f Dockerfile -t credit .```

* To run the image, use below command:
```docker run -p 9000:9000 credit```

And the service will be accessible at 
```http://localhost:9000/swagger-ui.html```
