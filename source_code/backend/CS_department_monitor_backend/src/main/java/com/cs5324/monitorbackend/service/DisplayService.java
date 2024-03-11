package com.cs5324.monitorbackend.service;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisplayService{
    @Resource
    private final PostService postService;
    @Resource
    private final PageService pageService;
    @Resource
    private final MediaService mediaService;
    @Resource
    private final EventService eventService;
}
