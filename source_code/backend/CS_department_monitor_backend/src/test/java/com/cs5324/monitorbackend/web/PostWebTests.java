package com.cs5324.monitorbackend.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class PostWebTests {
    WebTestClient webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    public void createNewPost_Success() {
        String postJson = "{" +
                "\"title\": \"Test Post\"," +
                "\"content\": \"Test Content Body\"" +
                "}";

        webTestClient.post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postJson)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.createdAt").isNotEmpty()
                .jsonPath("$.version").isNotEmpty();
    }

    @Test
    public void createNewPost_EmptyTitle() {
        String postJson = "{" +
                "\"title\": \"\"," +
                "\"content\": \"Test Content Body\"" +
                "}";

        webTestClient.post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postJson)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void createNewPost_EmptyContent() {
        String postJson = "{" +
                "\"title\": \"Test Post\"," +
                "\"content\": \"\"" +
                "}";

        webTestClient.post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postJson)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
