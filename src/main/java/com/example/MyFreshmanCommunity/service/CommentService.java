package com.example.MyFreshmanCommunity.service;

import com.example.MyFreshmanCommunity.dto.ArticleResponseDto;
import com.example.MyFreshmanCommunity.dto.CommentDto;
import com.example.MyFreshmanCommunity.dto.CommentResponseDto;
import com.example.MyFreshmanCommunity.entity.Article;
import com.example.MyFreshmanCommunity.entity.Comment;
import com.example.MyFreshmanCommunity.entity.Member;
import com.example.MyFreshmanCommunity.exception.NotFoundException;
import com.example.MyFreshmanCommunity.exception.NotPermissionException;
import com.example.MyFreshmanCommunity.repository.ArticleRepository;
import com.example.MyFreshmanCommunity.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    //댓글 조회
    public List<CommentResponseDto> showAll(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        List<CommentResponseDto> dtos = new ArrayList<CommentResponseDto>();
        for (Comment c : comments) {
            CommentResponseDto dto = CommentResponseDto.createCommentDto(c);
            dtos.add(dto);
        }
        return dtos;
    }

    //댓글 생성
    public CommentResponseDto create(Long articleId, CommentDto commentDto, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Article article = articleRepository.findById(articleId).
                orElseThrow(() -> new NotFoundException("대상 게시글이 없습니다."));
        Comment comment = Comment.createComment(commentDto, member, article);
        Comment save = commentRepository.save(comment);
        return CommentResponseDto.createCommentDto(save);
    }

    //댓글 수정
    public CommentResponseDto update(Long commentId, CommentDto commentDto, HttpSession session) {
        Comment target = commentRepository.findById(commentId).
                orElseThrow(() -> new NotFoundException("대상 댓글이 없습니다."));
        Member member = (Member) session.getAttribute("member");

        if(!target.getMember().getId().equals(member.getId())) {
            throw new NotPermissionException("다른 사람의 댓글을 수정할 수 없습니다.");
        }

        target.patch(commentDto);

        Comment updated = commentRepository.save(target);
        return CommentResponseDto.createCommentDto(updated);
    }

    //댓글 삭제
    public void delete(Long commentId, HttpSession session) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new NotFoundException("대상 댓글이 없습니다."));
        Member member = (Member) session.getAttribute("member");

        if(!comment.getMember().getId().equals(member.getId())) {
            throw new NotPermissionException("다른 사람의 댓글을 삭제할 수 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
