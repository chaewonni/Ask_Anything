package com.example.MyFreshmanCommunity.service;

import com.example.MyFreshmanCommunity.dto.BookmarkDto;
import com.example.MyFreshmanCommunity.entity.Article;
import com.example.MyFreshmanCommunity.entity.Bookmark;
import com.example.MyFreshmanCommunity.entity.Member;
import com.example.MyFreshmanCommunity.exception.NotFoundException;
import com.example.MyFreshmanCommunity.repository.ArticleRepository;
import com.example.MyFreshmanCommunity.repository.BookmarkRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkDto addBookmark(Long articleId, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Article article = articleRepository.findById(articleId).
                orElseThrow(() -> new NotFoundException("대상 게시글이 없습니다."));

        if(bookmarkRepository.findByMemberAndArticle(member, article) == null) {
//            article.setBookmarkCount(article.getBookmarkCount() + 1);
            Bookmark bookmark = Bookmark.createBookmark(member, article);
            bookmarkRepository.save(bookmark);
            return BookmarkDto.createBookmarkDto("북마크 처리 완료", bookmark);
        }
        else {
//            article.setBookmarkCount(article.getBookmarkCount() - 1);
            Bookmark bookmark = bookmarkRepository.findByMemberAndArticle(member, article);
            bookmark.deleteBookmark(article); //false처리
            bookmarkRepository.deleteByMemberAndArticle(member, article);
            return BookmarkDto.createBookmarkDto("북마크 취소 완료", bookmark);
        }
    }
}
