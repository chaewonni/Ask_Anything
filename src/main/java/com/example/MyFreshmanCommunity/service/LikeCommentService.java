package com.example.MyFreshmanCommunity.service;

import com.example.MyFreshmanCommunity.dto.LikeDto;
import com.example.MyFreshmanCommunity.entity.Comment;
import com.example.MyFreshmanCommunity.entity.LikeComment;
import com.example.MyFreshmanCommunity.entity.Member;
import com.example.MyFreshmanCommunity.exception.NotFoundException;
import com.example.MyFreshmanCommunity.repository.CommentRepository;
import com.example.MyFreshmanCommunity.repository.LikeCommentRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Transactional
    public LikeDto addLike(Long commentId, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new NotFoundException("대상 댓글이 없습니다."));

        if (likeCommentRepository.findByMemberAndComment(member, comment) == null) { //좋아요가 없으면
            comment.setLikesCount(comment.getLikesCount() + 1); //1씩 증가
            LikeComment likeComment = LikeComment.createLike(member, comment); //true처리
            likeCommentRepository.save(likeComment);
            return LikeDto.createLikeDto("좋아요 처리 완료", likeComment);
        }
        else { //이미 좋아요 눌렀으면
            comment.setLikesCount(comment.getLikesCount() - 1);
            LikeComment likeComment = likeCommentRepository.findByMemberAndComment(member, comment);
            likeComment.deleteLike(comment); //false처리
            likeCommentRepository.deleteByMemberAndComment(member, comment);
            return LikeDto.createLikeDto("좋아요 취소 완료", likeComment);
        }
    }
}