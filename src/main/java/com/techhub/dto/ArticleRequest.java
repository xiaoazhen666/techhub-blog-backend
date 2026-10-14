package com.techhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ArticleRequest {

    @NotBlank(message = "标题不能为空")
    @Size(max = 200, message = "标题过长")
    private String title;

    @Size(max = 500, message = "摘要过长")
    private String summary;

    @NotBlank(message = "正文不能为空")
    private String content;

    private String cover;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    private List<String> tags;
}
