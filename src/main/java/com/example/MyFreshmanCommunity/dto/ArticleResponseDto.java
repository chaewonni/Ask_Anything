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
    private int bookmarkCount;
    private LocalDateTime createDate;
    // 유저의 메이저 정보를 포함
    private MemberInfoDto memberInfo;
    // 게시글의 메이저 정보를 포함 (유저의 메이저와 게시글의 메이저가 다를 수 있으므로)
    private Major boardMajor;

    public static ArticleResponseDto createArticleDto(Article n) {
        MemberInfoDto memberInfo = n.getMember() != null ?
                MemberInfoDto.createMemberDto(n.getMember()) : null; //Member가 null인 경우 MemberInfo를 null로 설정

        return new ArticleResponseDto(
                n.getId(),
                n.getTitle(),
                n.getContent(),
                n.getBookmarkCount(),
                n.getCreateDate(),
                memberInfo,
                n.getMajor()
        );
    }
}




