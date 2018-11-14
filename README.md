# gateway-netty-memory-leak-issues
Sample spring gateway application to investigate netty memory leak and sockets leak issue 

**What you need?** 

- java 1.8 and above
- wiremock standalone 
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
- Add below runtime parameters,
```
"-Dio.netty.leakDetectionLevel=advanced" "-Dreactor.ipc.netty.native=false" "-Dio.netty.leakDetection.targetRecords=20"
```
- Start the application (By default application will start on port 7007, if you want to change, then update `application.yml`)
- Test the service 
```
curl -X GET http://localhost:7007/v1/reward-memberships/abc
```
- Create load using jmeter 
- launch jvisualvm to monitor the application

