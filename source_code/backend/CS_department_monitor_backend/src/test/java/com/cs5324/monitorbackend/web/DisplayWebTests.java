package com.cs5324.monitorbackend.web;

import com.cs5324.monitorbackend.entity.Media;
import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.responsebody.GenericMediaResponse;
import com.cs5324.monitorbackend.responsebody.GenericPostResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DisplayWebTests {
    WebTestClient webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build();

    List<UUID> createdMediaIds = new ArrayList<>();
    List<UUID> createdPostIds = new ArrayList<>();

    static final int MEDIA_COUNT = 100;
    static final int POST_COUNT = 50;

    @Test
    @Order(-2)
    public void populateMedia() {
        GenericMediaResponse resp = webTestClient.post()
                .uri("/api/media/populate?mediaCount="+MEDIA_COUNT)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericMediaResponse.class)
                .returnResult().getResponseBody();

        assert resp != null;
        System.out.println(resp.getMediaIds().size());
        for(Media m : resp.getMedia()){
            createdMediaIds.add(m.getId());
        }
    }

    @Test
    @Order(-1)
    public void populatePosts() {
        GenericPostResponse resp = webTestClient.post()
                .uri("/api/posts/populate/approved?postCount="+POST_COUNT+"&userCount=4")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericPostResponse.class)
                .returnResult().getResponseBody();

        assert resp != null;
        System.out.println(resp.getPostIds().size());
        for(Post p : resp.getPosts()){
            createdPostIds.add(p.getId());
        }
    }

    @Test
    @Order(0)
    public void getMedia_onlyTenEntries_noTagged_allApproved() {
        GenericMediaResponse resp = webTestClient.get()
                .uri("/api/display/media")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericMediaResponse.class)
                .returnResult().getResponseBody();

        assert resp != null;
        assertTrue(resp.getCount() <= 10);
        for(Media m : resp.getMedia()){
            assertSame(ItemStatus.APPROVED, m.getItemStatus());
            assertFalse(m.getIsTagged());
        }
    }

    @Test
    @Order(0)
    public void getPosts_onlyTenEntries_noTagged_allApproved() {
        GenericPostResponse resp = webTestClient.get()
                .uri("/api/display/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericPostResponse.class)
                .returnResult().getResponseBody();

        assert resp != null;
        assertTrue(resp.getCount() <= 10);
        for(Post p : resp.getPosts()){
            assertSame(ItemStatus.APPROVED, p.getStatus());
            assertFalse(p.getIsTagged());
        }
    }

    @Test
    @Order(1)
    public void tagMedia_onlyApproved_stillTenEntries_tagFive() throws JSONException {

        GenericMediaResponse getResp = WebClient.create()
                .get()
                .uri("http://localhost:8080/api/media")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GenericMediaResponse.class)
                .block();
        assert getResp != null;
        for(Media m : getResp.getMedia()){
            createdMediaIds.add(m.getId());
        }


        JSONArray jArray = new JSONArray();
        for(int i = 0; i < 5; i++){
            jArray.put(createdMediaIds.get(i).toString());
        }

        String requestBody = new JSONObject()
                .put("ids", jArray)
                .toString();
        GenericMediaResponse resp = webTestClient.post()
                .uri("/api/display/media")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericMediaResponse.class)
                .returnResult().getResponseBody();

        assert resp != null;
        assertEquals(5, resp.getCount());
        for(Media m : resp.getMedia()){
            assertSame(ItemStatus.APPROVED, m.getItemStatus());
            assertTrue(m.getIsTagged());
            WebClient.create()
                    .delete()
                    .uri("http://localhost:8080/api/media/"+m.getId())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }
    }

    @Test
    @Order(1)
    public void tagPosts_onlyApproved_stillTenEntries_tagFive() throws JSONException {
        GenericPostResponse getResp = WebClient.create()
                .get()
                .uri("http://localhost:8080/api/posts/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GenericPostResponse.class)
                .block();
        assert getResp != null;
        for(Post p : getResp.getPosts()){
            createdPostIds.add(p.getId());
        }

        JSONArray jArray = new JSONArray();
        for(int i = 0; i < 5; i++){
            jArray.put(createdPostIds.get(i).toString());
        }

        String requestBody = new JSONObject()
                .put("ids", jArray)
                .toString();

        GenericPostResponse resp = webTestClient.post()
                .uri("/api/display/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenericPostResponse.class)
                .returnResult().getResponseBody();

        assert resp != null;
        assertEquals(5, resp.getCount());
        for(Post p : resp.getPosts()){
            assertSame(ItemStatus.APPROVED, p.getStatus());
            assertTrue(p.getIsTagged());
            WebClient.create()
                    .delete()
                    .uri("http://localhost:8080/api/posts/"+p.getId())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }
    }

    @AfterAll
    public static void tearDown(){
        GenericMediaResponse getRespMedia = WebClient.create()
                .get()
                .uri("http://localhost:8080/api/media")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GenericMediaResponse.class)
                .block();
        assert getRespMedia != null;
        for(Media m : getRespMedia.getMedia()){
            WebClient.create()
                    .delete()
                    .uri("http://localhost:8080/api/media/"+m.getId())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }

        GenericPostResponse getRespPost = WebClient.create()
                .get()
                .uri("http://localhost:8080/api/posts/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GenericPostResponse.class)
                .block();
        assert getRespPost != null;
        for(Post p : getRespPost.getPosts()){
            WebClient.create()
                    .delete()
                    .uri("http://localhost:8080/api/posts/"+p.getId())
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }
    }
}
