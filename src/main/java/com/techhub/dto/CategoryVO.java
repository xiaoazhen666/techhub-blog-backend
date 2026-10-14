package com.techhub.dto;

import com.techhub.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryVO {
    private Long id;
    private String name;
    private String description;
    private Integer articleCount;

    public static CategoryVO from(Category c) {
        return new CategoryVO(
                c.getId(),
                c.getName(),
                c.getDescription(),
                c.getArticles() != null ? c.getArticles().size() : 0
        );
    }
}
