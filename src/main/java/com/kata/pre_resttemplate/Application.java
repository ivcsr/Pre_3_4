package com.kata.pre_resttemplate;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        String sessionId = response.getHeaders().get("Set-Cookie").get(0);

        User user = new User(3L, "James", "Brown", (byte) 25);
        headers.add("Cookie", sessionId);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        String code1 = response.getBody();
        System.out.println(code1);

        user.setName("Thomas");
        user.setLastName("Shelby");
        response = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        String code2 = response.getBody();
        System.out.println(code2);

        response = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class);
        String code3 = response.getBody();
        System.out.println(code3);

        System.out.println(code1 + code2 + code3);
    }
}
