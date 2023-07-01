package dev.konstantinou.urlshortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HealthApiTest {

    @Value("${server.api-prefix}")
    private String apiPrefix;


    @LocalServerPort
    private int port;

    @Test
    void checkIfHealthEndpointIsReachable() {
        System.out.println(port);
        given()
                .port(port)
                .get(apiPrefix +"/health")
                .then()
                .assertThat()
                .body("isUp", equalTo(true));
    }
}
