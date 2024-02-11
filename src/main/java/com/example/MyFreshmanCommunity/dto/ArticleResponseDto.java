package com.example.MyFreshmanCommunity.dto;

import com.example.MyFreshmanCommunity.entity.Article;
import com.example.MyFreshmanCommunity.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
//    private Long memberId;
    private LoginResponseDto member;
    private Major major;

    public static ArticleResponseDto createArticleDto(Article article){
        return new ArticleResponseDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreateDate(),
//                article.getMember().getId(), //게시글을 작성한 멤버의 id
//                article.getMember().getStudentId(),
//                article.getMember().getMajor().getId(),
////                게시글을 쓴 멤버도 불러와야해!! 멤버의 학과랑 멤버의 학번
                LoginResponseDto.createLoginDto(article.getMember()),
                article.getMajor()
        );
    }
}




