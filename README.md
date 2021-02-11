## Handle redirects(302 status) from apps behind a Spring Zuul proxy

### Start greetings application
1. cd greetings
2. run `./gradlew bootRun`
3. greetings service will start at port 8080

### Start zuul proxy application
1. cd spring-zuul-redirect
2. run `./gradlew bootRun`
3. spring zuul proxy service will start at port 9000
---

### Standard behaviour
1. `curl --location --request GET 'http://localhost:9000/api/greet/john'`
will return 404.
    ``` 
    This is because, the zuul proxy is not able to resolve the /hello/john url from the *redirect location path* to any of its routes.
    Note : Even though the LocationRewriteFilter bean has been added in the spring boot application class (SpringZuulRedirectApplication.java), the LocationRewriteFilter has been disabled in the application.yml.
    ```
---

### Handling Redirection using:
1. *x-forwarded-prefix* header (refer [spring cloud doc](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html)):
    - `curl --location --request GET 'http://localhost:9000/api/farewell/john'`
  should return `Bye john`
2. *LocationRewriteFilter* (refer [spring doc](https://docs.spring.io/spring-cloud-netflix/docs/2.2.4.RELEASE/reference/html/#zuul-redirect-location-rewrite)): 
    - Remove `LocationRewriteFilter` related config from zuul proxy service's application.yml and restart the zuul proxy service.
    - `curl --location --request GET 'http://localhost:9000/api/greet/john'` should return Hello John
    - Caveat of using `LocationRewriteFilter` is that 
        ```. 
      As mentioned in the docs, this filter acts on the Location header of ALL 3XX response codes, which may not be appropriate in all scenarios, such as when redirecting the user to an external URL.
        ```
        - This can be tested by running `curl --location --request GET 'http://localhost:9000/api/go-to-google'`. This will return a 404 not found error, stating "/" is not found.
