package com.techhub.dto;

import com.techhub.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentVO {
    private Long id;
    private String content;
    private Integer likes;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private String userAvatar;

    public static CommentVO from(Comment c) {
        return new CommentVO(
                c.getId(),
                c.getContent(),
                c.getLikes(),
                c.getCreatedAt(),
                c.getUser().getId(),
                c.getUser().getNickname() != null ? c.getUser().getNickname() : c.getUser().getUsername(),
                c.getUser().getAvatar()
        );
    }
}
