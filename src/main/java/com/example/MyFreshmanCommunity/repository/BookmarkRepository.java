package com.example.MyFreshmanCommunity.repository;

import com.example.MyFreshmanCommunity.entity.Article;
import com.example.MyFreshmanCommunity.entity.Bookmark;
import com.example.MyFreshmanCommunity.entity.Member;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.Collection;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Bookmark findByMemberAndArticle(Member member, Article article);

    void deleteByMemberAndArticle(Member member, Article article);

    List<Bookmark> findAllByMemberOrderByCreateDateDesc(Member member);
}
