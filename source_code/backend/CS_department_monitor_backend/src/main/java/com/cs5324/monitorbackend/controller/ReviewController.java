package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.entity.Notification;
import com.cs5324.monitorbackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/reviews", produces = "application/json")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Iterable<Notification>> getAllNotifications() {
        try {
            return ResponseEntity.ok(reviewService.getAllNotifications());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping(params = "isApproved", consumes = "application/json")
    public ResponseEntity<Notification> reviewForApproval(@RequestBody Notification notification, @RequestParam(name = "isApproved") boolean isApproved) {
        try {
            return ResponseEntity.ok(reviewService.reviewForApproval(notification, isApproved));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
