package com.techhub.dto;

import com.techhub.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagVO {
    private Long id;
    private String name;
    private Integer articleCount;

    public static TagVO from(Tag t) {
        return new TagVO(
                t.getId(),
                t.getName(),
                t.getArticles() != null ? t.getArticles().size() : 0
        );
    }
}
