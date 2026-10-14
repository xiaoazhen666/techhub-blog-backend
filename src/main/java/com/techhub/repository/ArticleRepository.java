package com.techhub.repository;

import com.techhub.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.category.id = :categoryId ORDER BY a.pinned DESC, a.createdAt DESC")
    Page<Article> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT a FROM Article a ORDER BY a.pinned DESC, a.createdAt DESC")
    Page<Article> findAllByOrder(Pageable pageable);

    @Query("SELECT a FROM Article a ORDER BY a.views DESC, a.createdAt DESC")
    Page<Article> findHot(Pageable pageable);

    Page<Article> findByAuthorId(Long authorId, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% OR a.summary LIKE %:keyword% OR a.content LIKE %:keyword%")
    Page<Article> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.name = :tagName")
    Page<Article> findByTagName(@Param("tagName") String tagName, Pageable pageable);
}
