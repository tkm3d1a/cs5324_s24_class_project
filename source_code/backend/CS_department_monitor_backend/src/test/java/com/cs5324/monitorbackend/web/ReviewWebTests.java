package com.cs5324.monitorbackend.web;

import com.cs5324.monitorbackend.entity.Event;
import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewWebTests {
    WebTestClient webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    public void getAllUnapprovedNotifications_Success() {
        Event e = new Event();
        e.setDateOfEvent(LocalDate.of(2022, 1, 1));

        webTestClient.post()
                .uri("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(e)
                .exchange();


        List<Notification> notifications = webTestClient.get()
                .uri("/api/reviews")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Notification.class)
                .getResponseBody()
                .collectList()
                .block();

        assertNotNull(notifications);
        notifications.forEach(n -> {
            Post p = n.getPost();
            Event event = n.getEvent();
            Media m = n.getMedia();

            if (p != null) {
                assertNotSame(p.getStatus(), ItemStatus.APPROVED);
            } else if (event != null) {
                assertNotSame(event.getApprovalStatus(), ItemStatus.APPROVED);
            } else if (m != null) {
                assertNotSame(m.getItemStatus(), ItemStatus.APPROVED);
            }
        });
    }

    @Test
    public void reviewForApproval_Success() {
        Event e = new Event();
        e.setDateOfEvent(LocalDate.of(2022, 1, 1));

        webTestClient.post()
                .uri("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(e)
                .exchange();

        List<Notification> notifications = webTestClient.get()
                .uri("/api/reviews")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Notification.class)
                .getResponseBody()
                .collectList()
                .block();

        assertNotNull(notifications);

        UUID notificationId = notifications.get(0).getId();

        String notificationJson = "{" +
                "\"id\": \"" + notificationId + "\"" +
                "}";

        System.out.println(notificationJson);
        Notification notification = webTestClient.post()
                .uri("/api/reviews?isApproved=true")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(notificationJson)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Notification.class)
                .getResponseBody().blockFirst();

        assertNotNull(notification);

        Event notificationEvent = notification.getEvent();

        assertSame(notificationEvent.getApprovalStatus(), ItemStatus.APPROVED);
    }
}
