package com.techhub.controller;

import com.techhub.dto.*;
import com.techhub.entity.User;
import com.techhub.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ApiResponse<PageResult<ArticleVO>> list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "new") String sort) {
        return ApiResponse.ok(articleService.list(categoryId, page, size, sort));
    }

    @GetMapping("/{id}")
    public ApiResponse<ArticleDetailVO> detail(@PathVariable Long id) {
        return ApiResponse.ok(articleService.getById(id));
    }

    @GetMapping("/search")
    public ApiResponse<PageResult<ArticleVO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(articleService.search(keyword, page, size));
    }

    @GetMapping("/tag/{tagName}")
    public ApiResponse<PageResult<ArticleVO>> byTag(
            @PathVariable String tagName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(articleService.byTag(tagName, page, size));
    }

    @PostMapping
    public ApiResponse<ArticleVO> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ArticleRequest request) {
        return ApiResponse.ok("发布成功", articleService.create(user, request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ArticleVO> update(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request) {
        return ApiResponse.ok("更新成功", articleService.update(user, id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        articleService.delete(user, id);
        return ApiResponse.ok("删除成功", null);
    }

    @PostMapping("/{id}/like")
    public ApiResponse<Void> like(@PathVariable Long id) {
        articleService.like(id);
        return ApiResponse.ok("点赞成功", null);
    }
}
