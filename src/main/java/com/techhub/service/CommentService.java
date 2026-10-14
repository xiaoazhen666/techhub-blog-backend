package com.techhub.service;

import com.techhub.dto.CommentRequest;
import com.techhub.dto.CommentVO;
import com.techhub.dto.PageResult;
import com.techhub.entity.Article;
import com.techhub.entity.Comment;
import com.techhub.entity.User;
import com.techhub.exception.BusinessException;
import com.techhub.repository.ArticleRepository;
import com.techhub.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public PageResult<CommentVO> listByArticle(Long articleId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Comment> result = commentRepository.findByArticleId(articleId, pageable);
        return PageResult.of(result.map(CommentVO::from));
    }

    @Transactional
    public CommentVO create(User user, Long articleId, CommentRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(404, "文章不存在"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .article(article)
                .build();
        commentRepository.save(comment);

        article.setComments(article.getComments() + 1);
        articleRepository.save(article);

        return CommentVO.from(comment);
    }

    @Transactional
    public void delete(User user, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "评论不存在"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BusinessException(403, "无权删除他人评论");
        }
        commentRepository.delete(comment);
    }
}
