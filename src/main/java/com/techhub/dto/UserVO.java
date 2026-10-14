package com.techhub.dto;

import com.techhub.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String bio;
    private String level;
    private Integer followers;
    private Integer following;
    private Integer likes;

    public static UserVO from(User u) {
        return new UserVO(
                u.getId(),
                u.getUsername(),
                u.getNickname(),
                u.getAvatar(),
                u.getBio(),
                u.getLevel(),
                u.getFollowers(),
                u.getFollowing(),
                u.getLikes()
        );
    }
}
