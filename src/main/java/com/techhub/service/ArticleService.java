package com.techhub.service;

import com.techhub.dto.ArticleDetailVO;
import com.techhub.dto.ArticleRequest;
import com.techhub.dto.ArticleVO;
import com.techhub.dto.PageResult;
import com.techhub.entity.Article;
import com.techhub.entity.Category;
import com.techhub.entity.Tag;
import com.techhub.entity.User;
import com.techhub.exception.BusinessException;
import com.techhub.repository.ArticleRepository;
import com.techhub.repository.CategoryRepository;
import com.techhub.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public PageResult<ArticleVO> list(Long categoryId, int page, int size, String sort) {
        Sort.Order order = "hot".equals(sort)
                ? Sort.Order.desc("views")
                : Sort.Order.desc("createdAt");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("pinned"), order));

        Page<Article> result = categoryId != null && categoryId > 0
                ? articleRepository.findByCategoryId(categoryId, pageable)
                : articleRepository.findAllByOrder(pageable);

        return PageResult.of(result.map(ArticleVO::from));
    }

    @Transactional
    public ArticleDetailVO getById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "文章不存在"));
        article.setViews(article.getViews() + 1);
        articleRepository.save(article);
        return ArticleDetailVO.from(article);
    }

    @Transactional
    public ArticleVO create(User author, ArticleRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BusinessException("分类不存在"));

        Article article = Article.builder()
                .title(request.getTitle())
                .summary(request.getSummary())
                .content(request.getContent())
                .cover(request.getCover())
                .author(author)
                .category(category)
                .build();

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            List<Tag> tags = new ArrayList<>();
            for (String name : request.getTags()) {
                Tag tag = tagRepository.findByName(name.trim())
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name.trim()).build()));
                tags.add(tag);
            }
            article.setTags(tags);
        }

        articleRepository.save(article);
        return ArticleVO.from(article);
    }

    @Transactional
    public ArticleVO update(User author, Long id, ArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "文章不存在"));

        if (!article.getAuthor().getId().equals(author.getId())) {
            throw new BusinessException(403, "无权修改他人文章");
        }

        article.setTitle(request.getTitle());
        article.setSummary(request.getSummary());
        article.setContent(request.getContent());
        article.setCover(request.getCover());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException("分类不存在"));
            article.setCategory(category);
        }

        if (request.getTags() != null) {
            List<Tag> tags = new ArrayList<>();
            for (String name : request.getTags()) {
                Tag tag = tagRepository.findByName(name.trim())
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name.trim()).build()));
                tags.add(tag);
            }
            article.setTags(tags);
        }

        articleRepository.save(article);
        return ArticleVO.from(article);
    }

    @Transactional
    public void delete(User author, Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "文章不存在"));
        if (!article.getAuthor().getId().equals(author.getId())) {
            throw new BusinessException(403, "无权删除他人文章");
        }
        articleRepository.delete(article);
    }

    @Transactional
    public void like(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "文章不存在"));
        article.setLikes(article.getLikes() + 1);
        articleRepository.save(article);
    }

    public PageResult<ArticleVO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Article> result = articleRepository.search(keyword, pageable);
        return PageResult.of(result.map(ArticleVO::from));
    }

    public PageResult<ArticleVO> byTag(String tagName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Article> result = articleRepository.findByTagName(tagName, pageable);
        return PageResult.of(result.map(ArticleVO::from));
    }

    public PageResult<ArticleVO> listByAuthor(Long authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Article> result = articleRepository.findByAuthorId(authorId, pageable);
        return PageResult.of(result.map(ArticleVO::from));
    }
}
