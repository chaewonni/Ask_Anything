package com.example.MyFreshmanCommunity.api;

import com.example.MyFreshmanCommunity.dto.LikeDto;
import com.example.MyFreshmanCommunity.service.LikeCommentService;
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
public class LikeCommentApiController {

    private final LikeCommentService likeCommentService;

    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<LikeDto> addLike(@PathVariable Long commentId, HttpServletRequest request) {

        HttpSession session = request.getSession();
        LikeDto like = likeCommentService.addLike(commentId, session);
        return ResponseEntity.status(HttpStatus.OK).body(like);
    }

}
