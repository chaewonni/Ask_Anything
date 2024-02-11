package com.example.MyFreshmanCommunity.api;

import com.example.MyFreshmanCommunity.dto.CommentDto;
import com.example.MyFreshmanCommunity.dto.CommentResponseDto;
import com.example.MyFreshmanCommunity.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    //댓글 조회
    @GetMapping("/article/{articleId}/comment")
    public ResponseEntity<List<CommentResponseDto>> showAll(@PathVariable Long articleId){
        List<CommentResponseDto> comments = commentService.showAll(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    //댓글 생성
    @PostMapping("/article/{articleId}/comment")
    public ResponseEntity<CommentResponseDto> create(@PathVariable Long articleId, @RequestBody CommentDto commentDto,
                                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        CommentResponseDto comment = commentService.create(articleId, commentDto, session);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    //댓글 수정
    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long commentId, @RequestBody CommentDto commentDto,
                                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        CommentResponseDto comment = commentService.update(commentId, commentDto, session);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> delete(@PathVariable Long commentId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        commentService.delete(commentId, session);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 완료");
    }

}
