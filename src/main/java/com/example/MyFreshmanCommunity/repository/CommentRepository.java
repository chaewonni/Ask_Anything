package com.example.MyFreshmanCommunity.repository;

import com.example.MyFreshmanCommunity.dto.CommentDto;
import com.example.MyFreshmanCommunity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //좋아요가 10개 이상이면 베스트댓글
    @Query(value = "SELECT * FROM comment " +
            "WHERE article_id = :articleId " +
            "ORDER BY CASE WHEN likes_count >= 10 THEN 0 ELSE 1 END, " +
            "CASE WHEN likes_count >= 10 THEN likes_count ELSE NULL END DESC, " +
            "create_date ASC",
            nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);
}
