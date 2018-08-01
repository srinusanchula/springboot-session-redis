package com.citrix.sbsession.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HelloRestControllerTest {

    @Autowired
    WebTestClient webClient;

    @LocalServerPort
    private int port;

    @Test
    public void userRequestWithProperCredentials_ThenSuccess() throws Exception {
        this.webClient.get()
        .uri("/hello")
        .header("Authorization", "Basic " + Base64Utils.encodeToString(("user:password").getBytes("UTF-8")))
        .exchange().expectStatus().isOk();
    }

    @Test
    public void userRequesWithtWrongCredentials_thenUnauthorized() throws Exception {
        this.webClient.get()
        .uri("/hello")
        .header("Authorization", "Basic " + Base64Utils.encodeToString(("user:junk").getBytes("UTF-8")))
        .exchange().expectStatus().isUnauthorized();
    }

    @Test
    public void userRequestWithSessionCookie_ThenSuccess() throws Exception {
        TestRestTemplate restTemplate = new TestRestTemplate("user", "password");
        HttpHeaders headers = restTemplate.getForEntity("http://localhost:" + port + "/hello", String.class).getHeaders();

        assertTrue(headers.containsKey("Set-Cookie"));
        String []sessionToken = headers.get("Set-Cookie").get(0).split(";")[0].split("=", 2);
        
        this.webClient.get()
        .uri("/hello")
        .cookie(sessionToken[0], sessionToken[1])
        .exchange()
        .expectStatus().isOk();
    }

}