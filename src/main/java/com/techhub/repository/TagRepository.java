package com.techhub.repository;

import com.techhub.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("SELECT COUNT(a) FROM Article a JOIN a.tags t WHERE t.id = :tagId")
    long countArticlesByTagId(Long tagId);
}
