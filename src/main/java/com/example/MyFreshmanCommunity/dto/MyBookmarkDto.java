package com.example.MyFreshmanCommunity.dto;

import com.example.MyFreshmanCommunity.entity.Article;
import com.example.MyFreshmanCommunity.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBookmarkDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LoginResponseDto member;
    private Major major;

    public static MyBookmarkDto createMyBookmarkDto(Article article){
        return new MyBookmarkDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreateDate(),
                LoginResponseDto.createLoginDto(article.getMember()),
                article.getMajor()
        );
    }
}




