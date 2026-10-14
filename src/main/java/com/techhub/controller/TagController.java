package com.techhub.controller;

import com.techhub.dto.ApiResponse;
import com.techhub.entity.Tag;
import com.techhub.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping
    public ApiResponse<List<Tag>> list() {
        return ApiResponse.ok(tagRepository.findAll());
    }
}
