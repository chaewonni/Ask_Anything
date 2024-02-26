package com.example.MyFreshmanCommunity.service;

import com.example.MyFreshmanCommunity.dto.ArticleDto;
import com.example.MyFreshmanCommunity.dto.ArticleResponseDto;
import com.example.MyFreshmanCommunity.entity.Article;
import com.example.MyFreshmanCommunity.entity.Major;
import com.example.MyFreshmanCommunity.entity.Member;
import com.example.MyFreshmanCommunity.exception.MemberNotFoundException;
import com.example.MyFreshmanCommunity.exception.NotFoundException;
import com.example.MyFreshmanCommunity.exception.NotPermissionException;
import com.example.MyFreshmanCommunity.repository.ArticleRepository;
import com.example.MyFreshmanCommunity.repository.MajorRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MajorRepository majorRepository;

    //게시글 전체 조회
    public List<ArticleResponseDto> showAll(Long majorId) {
        List<Article> articles = articleRepository.findAllByMajorId(majorId);
        List<ArticleResponseDto> dtos = new ArrayList<ArticleResponseDto>();
        for (Article a : articles) {
            ArticleResponseDto dto = ArticleResponseDto.createArticleDto(a);
            dtos.add(dto);
        }
        return dtos;
    }

    //게시글 단건 조회
    public ArticleResponseDto show(Long majorId, Long articleId) {
        Article article = articleRepository.findByArticleId(majorId, articleId);
        ArticleResponseDto dto = ArticleResponseDto.createArticleDto(article);

        return dto;
    }

    //게시글 생성
    public void create(Long majorId, ArticleDto articleDto, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Major major = majorRepository.findById(majorId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 전공입니다"));
        if(member == null) throw new MemberNotFoundException("로그인 하지 않은 상태에서 글을 쓸 수 없습니다.");
        Article article = Article.createArticle(articleDto, member, major);
        articleRepository.save(article);
    }

    //게시글 수정
    public ArticleResponseDto update(Long majorId, Long articleId, ArticleDto articleDto, HttpSession session) {
        Article target = articleRepository.findByArticleId(majorId, articleId);
        Member member = (Member) session.getAttribute("member");

        log.info("Target Member ID: {}, Name: {}, Email:{}, Password:{}, studentId:{}, major:{}", target.getMember().getId(), target.getMember().getMemberName(), target.getMember().getEmail(), target.getMember().getPassword(), target.getMember().getStudentId(), target.getMember().getMajor().getId());
        log.info("Session Member ID: {}, Name: {}, Email:{}, Password:{}, studentId:{}, major:{}", member.getId(), member.getMemberName(), member.getEmail(), member.getPassword(), member.getStudentId(), member.getMajor().getId());

        if(target == null) {
            throw new IllegalArgumentException("대상 게시글이 없습니다.");
        }
        if(!target.getMember().getId().equals(member.getId())) {
            throw new NotPermissionException("다른 사람의 게시물을 수정할 수 없습니다.");
        }

        target.patch(articleDto);

        Article updated = articleRepository.save(target);
        return ArticleResponseDto.createArticleDto(updated);
    }

    //게시글 삭제
    public void delete(Long majorId, Long articleId, HttpSession session) {
        Article article = articleRepository.findByArticleId(majorId, articleId);
        Member member = (Member) session.getAttribute("member");

        if(!article.getMember().getId().equals(member.getId())) {
            throw new NotPermissionException("다른 사람의 게시물을 삭제할 수 없습니다.");
        }

        articleRepository.delete(article);
    }
}
