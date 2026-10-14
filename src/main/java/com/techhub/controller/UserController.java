package com.techhub.controller;

import com.techhub.dto.ApiResponse;
import com.techhub.dto.ArticleVO;
import com.techhub.dto.PageResult;
import com.techhub.dto.UserVO;
import com.techhub.entity.User;
import com.techhub.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ArticleService articleService;

    // 获取当前登录用户信息
    @GetMapping("/me")
    public ApiResponse<UserVO> me(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(UserVO.from(user));
    }

    // 获取当前登录用户发布的文章
    @GetMapping("/me/articles")
    public ApiResponse<PageResult<ArticleVO>> myArticles(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.ok(articleService.listByAuthor(user.getId(), page, size));
    }
}
