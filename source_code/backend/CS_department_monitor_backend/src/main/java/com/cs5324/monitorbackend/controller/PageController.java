package com.cs5324.monitorbackend.controller;

import com.cs5324.monitorbackend.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/pages", produces = "application/json")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;
}
