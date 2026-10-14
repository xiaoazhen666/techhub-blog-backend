package com.techhub.dto;

import com.techhub.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleVO {
    private Long id;
    private String title;
    private String summary;
    private String cover;
    private Integer views;
    private Integer likes;
    private Integer comments;
    private Boolean pinned;
    private LocalDateTime createdAt;
    private Long authorId;
    private String authorName;
    private String authorAvatar;
    private String categoryName;
    private List<String> tags;

    public static ArticleVO from(Article a) {
        return new ArticleVO(
                a.getId(),
                a.getTitle(),
                a.getSummary(),
                a.getCover(),
                a.getViews(),
                a.getLikes(),
                a.getComments(),
                a.getPinned(),
                a.getCreatedAt(),
                a.getAuthor().getId(),
                a.getAuthor().getNickname() != null ? a.getAuthor().getNickname() : a.getAuthor().getUsername(),
                a.getAuthor().getAvatar(),
                a.getCategory() != null ? a.getCategory().getName() : null,
                a.getTags().stream().map(t -> t.getName()).toList()
        );
    }
}
