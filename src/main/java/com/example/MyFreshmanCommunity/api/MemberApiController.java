package com.example.MyFreshmanCommunity.api;

import com.example.MyFreshmanCommunity.dto.LoginDto;
import com.example.MyFreshmanCommunity.dto.LoginResponseDto;
import com.example.MyFreshmanCommunity.dto.MyBookmarkDto;
import com.example.MyFreshmanCommunity.dto.SignupDto;
import com.example.MyFreshmanCommunity.entity.Member;
import com.example.MyFreshmanCommunity.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto) {
        memberService.signup(signupDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    //로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletRequest request){

        Member member = memberService.login(loginDto);

        if (member != null) {
            // 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("member", member);
            LoginResponseDto response = LoginResponseDto.createLoginDto(member);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else{
            // 로그인 실패
//            HttpSession session = request.getSession();
//            session.removeAttribute("member");
//            session.invalidate();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    //로그아웃
    @PostMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        memberService.logout(session);
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공");
    }

    //회원탈퇴
    @DeleteMapping("/user/delete")
    public ResponseEntity<String> delete(HttpSession session){
        Member member = (Member) session.getAttribute("member");

        if (member != null){
            memberService.delete(member.getId());
            session.invalidate();
            return ResponseEntity.status(HttpStatus.OK).body("탈퇴 완료");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    //나의 북마크
    @GetMapping("/user/bookmark")
    public ResponseEntity<List<MyBookmarkDto>> myBookmark(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<MyBookmarkDto> bookmarks = memberService.myBookmark(session);
        return ResponseEntity.status(HttpStatus.OK).body(bookmarks);
    }
}
