#Spike `LocationRewriteFilter` zuul bean behaviour

##Start Zuul proxy application
1. cd spring-zuul-redirect
2. run ./gradlew build 
3. run ./gradlew bootRun
4. Proxy service will start at in port 9000

##Start greetings application
1. cd greetings
2. run ./gradlew build 
3. run ./gradlew bootRun
4. Greetings service will start at in port 8080

##Testing
`curl --location --request GET 'http://localhost:9000/api/greet/john'`
should return `Hello john`


Without the `LocationRewriteFilter` bean the above curl will return a 404 error 
