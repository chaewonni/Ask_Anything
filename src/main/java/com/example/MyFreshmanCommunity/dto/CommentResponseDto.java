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
//    private Long memberId;
//    private String studentId;
//    private Major major;
    private LoginResponseDto member;

    public static CommentResponseDto createCommentDto(Comment comment){
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreateDate(),
                comment.getLikesCount(),
//                comment.getMember().getId(),
//                comment.getMember().getStudentId(),
//                comment.getMember().getMajor()
                LoginResponseDto.createLoginDto(comment.getMember())
        );
    }
}
