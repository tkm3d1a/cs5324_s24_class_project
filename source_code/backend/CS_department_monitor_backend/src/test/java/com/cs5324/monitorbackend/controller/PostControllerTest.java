package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Post;
import com.cs5324.monitorbackend.entity.enums.ItemStatus;
import com.cs5324.monitorbackend.responsebody.PostResponse;
import com.cs5324.monitorbackend.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private final PostResponse mockPostResponse = PostResponse.builder()
            .id(UUID.fromString("a4ebf689-969c-436b-aacb-4f231b0bd6c4"))
            .createdBy(UUID.fromString("9139a75b-3658-492f-b624-bdf69dc79c9e"))
            .title("Awesome Sauce").content("Heinz heinzz heinzzz")
            .status(ItemStatus.PENDING).isTagged(false)
            .createdOn(LocalDateTime.of(2024, 4, 10, 10, 11))
            .build();

    private final String postResponseJson = """
            {
                "id": "a4ebf689-969c-436b-aacb-4f231b0bd6c4",
                "createdBy": "9139a75b-3658-492f-b624-bdf69dc79c9e",
                "title": "Awesome Sauce",
                "content": "Heinz heinzz heinzzz",
                "status": "PENDING",
                "isTagged": false,
                "createdOn": "2024-04-10T10:11:00"
            }
            """;

    @Test
    void viewPostsShouldReturnPostsForUser() throws Exception {
        when(postService.getPostsForUser(any(UUID.class))).thenReturn(Collections.singletonList(mockPostResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts?userId=2bcc6af8-4c97-4afc-96af-b03a128e8a32"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"))
                .andExpect(MockMvcResultMatchers.content().json("[" + postResponseJson + "]"));
    }

    @Test
    void editPostsShouldUpdateAndReturnUpdatedPost() throws Exception {
        when(postService.editPost(any(Post.class))).thenReturn(mockPostResponse);

        String postJson = """
                {
                    "id": "a4ebf689-969c-436b-aacb-4f231b0bd6c4",
                    "title": "Awesome Sauce",
                    "content": "Heinz heinzz heinzzz"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/posts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(postJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(postResponseJson));
    }
}