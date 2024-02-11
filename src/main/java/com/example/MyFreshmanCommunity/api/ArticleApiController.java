package com.example.MyFreshmanCommunity.api;


import com.example.MyFreshmanCommunity.dto.ArticleDto;
import com.example.MyFreshmanCommunity.dto.ArticleResponseDto;
import com.example.MyFreshmanCommunity.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    //게시글 전체조회
    @GetMapping("/article/{majorId}")
    public ResponseEntity<List<ArticleResponseDto>> showAll(@PathVariable Long majorId) {
        List<ArticleResponseDto> articles = articleService.showAll(majorId);
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    //게시글 단건조회
    @GetMapping("/article/{majorId}/{articleId}")
    public ResponseEntity<ArticleResponseDto> show(@PathVariable Long majorId, @PathVariable Long articleId) {
        ArticleResponseDto article = articleService.show(majorId, articleId);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    //게시글 생성
    @PostMapping("/article/{majorId}")
    public ResponseEntity<String> create(@PathVariable Long majorId, @RequestBody ArticleDto articleDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        articleService.create(majorId, articleDto, session);
        return ResponseEntity.status(HttpStatus.OK).body("게시글 생성 완료");
    }

    //게시글 수정
    @PatchMapping("/article/{majorId}/{articleId}")
    public ResponseEntity<ArticleResponseDto> update(@PathVariable Long majorId, @PathVariable Long articleId,
                                             @RequestBody ArticleDto articleDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ArticleResponseDto article = articleService.update(majorId, articleId, articleDto, session);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    //게시글 삭제
    @DeleteMapping("/article/{majorId}/{articleId}")
    public ResponseEntity<String> delete(@PathVariable Long majorId, @PathVariable Long articleId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        articleService.delete(majorId, articleId, session);
        return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 완료");
    }
}
