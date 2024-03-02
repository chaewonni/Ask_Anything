package com.example.MyFreshmanCommunity.dto;

import com.example.MyFreshmanCommunity.entity.Article;
import com.example.MyFreshmanCommunity.entity.Comment;
import com.example.MyFreshmanCommunity.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private int likesCount;
    private MemberInfoDto memberInfo;
    private LoginResponseDto member;

    public static CommentResponseDto createCommentDto(Comment comment){

        MemberInfoDto memberInfo = comment.getMember() != null ?
                MemberInfoDto.createMemberDto(comment.getMember()) : null;

        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreateDate(),
                comment.getLikesCount(),
                memberInfo,
                LoginResponseDto.createLoginDto(comment.getMember())
        );
    }
}
