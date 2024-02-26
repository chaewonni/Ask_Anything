package com.example.MyFreshmanCommunity.api;

import com.example.MyFreshmanCommunity.dto.BookmarkDto;
import com.example.MyFreshmanCommunity.service.BookmarkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkApiController {

    private final BookmarkService bookmarkService;

    @PostMapping("/article/{articleId}/bookmark")
    public ResponseEntity<BookmarkDto> addBookmark(@PathVariable Long articleId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        BookmarkDto bookmark = bookmarkService.addBookmark(articleId, session);
        return ResponseEntity.status(HttpStatus.OK).body(bookmark);
    }
}
