# gateway-netty-memory-leak-issues
Sample spring gateway application to investigate netty memory leak and sockets leak issue 

**What you need?** 

- java 1.8 and above
- wiremock standalone - Refer to `http://wiremock.org/docs/running-standalone/`
- jmeter - to create load
- jvisualvm

**To run the application**

- Add mapping file to `mapping` directory alongside your standalone wiremock jar 
```
File name - get_membership_abc.json

Content of the file - 
{
    "request": {
        "method": "GET",
        "urlPath": "/v1/reward-memberships/abc"
    },
    "response": {
         "status": 200,
         "bodyFileName": "get_membership_abc_response_200.json",
         "fixedDelayMilliseconds": 1000,
         "headers": {
             "Content-Type": "application/json"
         }
    }
}
```
- Add response file to `__files` directory alongside your standalone wiremock jar
```
Copy file from repo - get_membership_abc_response_200.json
```
- Start wiremock server (When you change the port, ensure to update the new port in `application.yml`)
```
java -jar wiremock-standalone-2.18.0.jar --port 7006
```
- Build and start the application (By default application will start on port 7007, if you want to change, then update `application.yml`) from project root location
```
gradle clean build
java -jar "-Dio.netty.leakDetectionLevel=advanced" "-Dreactor.ipc.netty.native=false" "-Dio.netty.leakDetection.targetRecords=20" -Xmx256m build/libs/gateway-netty-memory-leak-issues-1.8.jar
```
- Test the service 
```
curl -X GET http://localhost:7007/v1/reward-memberships/abc
```
- Create load using jmeter
- launch jvisualvm to monitor the application

**How to reproduce the issue?**
- generating load using `jemeter` (500 requests ramped up in 60 secs continued same the load for 30 iterations)
- Start the service by applying varuois JVM parameters and enabling/disabling filters configured in `application.yml`
  - Test 1 - 256mb JVM includes micro-meter, includes two filters (Took ~5 mins to fail)
  - Test 2 - 512mb JVM includes micro-meter, includes all filters (Took ~15 mins to fail)
  - Test 3 - 256mb JVM excludes micro-meter, includes all filters (Took ~8 mins to fail)
  - Test 4 - 256mb JVM excludes micro-meter & excludes filter - SampleResponseDecoratorGatewayFilter  (Took ~8 mins to fail)
  - Test 5 - 256mb JVM excludes micro-meter & excludes filter - SampleWriteResponseGatewayFilter (Took ~8 mins to fail)
- No memory leaks found. Server was running over 1 hours without any issues 
  - Test 6 - 256mb JVM excludes micro-meter & removed all filters - 


