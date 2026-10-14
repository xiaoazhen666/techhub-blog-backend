package com.techhub.controller;

import com.techhub.dto.ApiResponse;
import com.techhub.dto.CommentRequest;
import com.techhub.dto.CommentVO;
import com.techhub.dto.PageResult;
import com.techhub.entity.User;
import com.techhub.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/article/{articleId}")
    public ApiResponse<PageResult<CommentVO>> list(
            @PathVariable Long articleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(commentService.listByArticle(articleId, page, size));
    }

    @PostMapping("/article/{articleId}")
    public ApiResponse<CommentVO> create(
            @AuthenticationPrincipal User user,
            @PathVariable Long articleId,
            @Valid @RequestBody CommentRequest request) {
        return ApiResponse.ok("评论成功", commentService.create(user, articleId, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        commentService.delete(user, id);
        return ApiResponse.ok("删除成功", null);
    }
}
