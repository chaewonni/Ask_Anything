package com.example.MyFreshmanCommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String title;
    private String content;
}
