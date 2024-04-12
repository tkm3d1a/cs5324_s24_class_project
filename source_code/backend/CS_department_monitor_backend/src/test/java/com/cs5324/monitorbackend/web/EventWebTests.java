package com.cs5324.monitorbackend.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class EventWebTests {
    WebTestClient webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    public void createEvent_Success() {
        String eventJson = "{" +
                "\"dateOfEvent\": \"2022-01-01\"" +
                "}";

        webTestClient.post()
                .uri("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(eventJson)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.dateOfEvent").isEqualTo("2022-01-01");
    }

    @Test
    public void createEvent_NullDateOfEvent() {
        String eventJson = "{" +
                "\"dateOfEvent\": \"\"" +
                "}";

        webTestClient.post()
                .uri("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(eventJson)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
