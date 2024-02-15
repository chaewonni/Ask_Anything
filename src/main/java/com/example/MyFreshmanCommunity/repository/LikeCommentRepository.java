package com.example.MyFreshmanCommunity.repository;

import com.example.MyFreshmanCommunity.entity.Comment;
import com.example.MyFreshmanCommunity.entity.LikeComment;
import com.example.MyFreshmanCommunity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    LikeComment findByMemberAndComment(Member member, Comment comment);

    void deleteByMemberAndComment(Member member, Comment comment);
}
