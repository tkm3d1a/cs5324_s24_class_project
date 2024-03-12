package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/reviews", produces = "application/json")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
}
