package com.example.MyFreshmanCommunity.repository;

import com.example.MyFreshmanCommunity.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //특정 전공의 모든 게시글 조회
    @Query(value = "SELECT * FROM article WHERE major_id = :majorId ORDER BY create_date DESC",
            nativeQuery = true)
    List<Article> findAllByMajorId(Long majorId);

    @Query(value = "SELECT * FROM article WHERE major_id = :majorId AND id = :articleId",
            nativeQuery = true)
    Article findByArticleId(@Param("majorId") Long majorId, @Param("articleId") Long articleId);
}
