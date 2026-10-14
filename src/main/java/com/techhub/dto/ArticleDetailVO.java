package com.techhub.dto;

import com.techhub.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleDetailVO {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String cover;
    private Integer views;
    private Integer likes;
    private Integer comments;
    private Boolean pinned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long authorId;
    private String authorName;
    private String authorAvatar;
    private String authorBio;
    private String categoryName;
    private List<String> tags;

    public static ArticleDetailVO from(Article a) {
        return new ArticleDetailVO(
                a.getId(),
                a.getTitle(),
                a.getSummary(),
                a.getContent(),
                a.getCover(),
                a.getViews(),
                a.getLikes(),
                a.getComments(),
                a.getPinned(),
                a.getCreatedAt(),
                a.getUpdatedAt(),
                a.getAuthor().getId(),
                a.getAuthor().getNickname() != null ? a.getAuthor().getNickname() : a.getAuthor().getUsername(),
                a.getAuthor().getAvatar(),
                a.getAuthor().getBio(),
                a.getCategory() != null ? a.getCategory().getName() : null,
                a.getTags().stream().map(t -> t.getName()).toList()
        );
    }
}
