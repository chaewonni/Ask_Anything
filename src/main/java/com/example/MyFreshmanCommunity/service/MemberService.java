package com.example.MyFreshmanCommunity.service;

import com.example.MyFreshmanCommunity.dto.LoginDto;
import com.example.MyFreshmanCommunity.dto.MyBookmarkDto;
import com.example.MyFreshmanCommunity.dto.SignupDto;
import com.example.MyFreshmanCommunity.entity.Major;
import com.example.MyFreshmanCommunity.entity.Member;
import com.example.MyFreshmanCommunity.exception.DuplicateMemberException;
import com.example.MyFreshmanCommunity.exception.IncorrectPasswordException;
import com.example.MyFreshmanCommunity.exception.NotFoundException;
import com.example.MyFreshmanCommunity.repository.ArticleRepository;
import com.example.MyFreshmanCommunity.repository.BookmarkRepository;
import com.example.MyFreshmanCommunity.repository.MajorRepository;
import com.example.MyFreshmanCommunity.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArticleRepository articleRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    //회원가입
    public void signup (SignupDto signupDto) {
        validateDuplicateMember(signupDto);
        signupDto.encodingPassword(passwordEncoder);
        Major major = majorRepository.findByMajorName(signupDto.getMajorName());
        if (major == null) {
            throw new IllegalStateException("전공이 존재하지 않습니다.");
        }
        //멤버 엔티티 생성
        Member member = Member.createMember(signupDto, major);
        //멤버 엔티티를 DB에 저장
        memberRepository.save(member);
//        Member signed = memberRepository.save(member);
//        //DTO로 변환 --> 필요없어서 주석처리
//        SignupDto.createMemberDto(signed);
    }

    //중복 검사
//    private void validateDuplicateMember(RegisterDto registerDto){ //실무에서는 한 번 더 최후의 방어(멀티스레드 상황 고려) -> 멤버의 네임을 유니크 제약조건으로 잡는 것을 권장
//        //EXCEPTION
//        List<Member> findMembers = memberRepository.findByEmail(registerDto.getEmail());
//        if(!findMembers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }

    private void validateDuplicateMember(SignupDto signupDto) {
        Long count = memberRepository.countByEmail(signupDto.getEmail());
        if (count > 0) {
            // 중복된 이메일이나 학번이 존재하는 경우 예외 발생
            throw new DuplicateMemberException("이미 존재하는 회원입니다.");
        }
    }

    //로그인
    public Member login (LoginDto loginDto) {
        Member member = memberRepository.findByEmail(loginDto.getEmail());
        if (member == null)
            throw new NotFoundException("존재하지 않는 회원입니다.");
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword()))
            throw new IncorrectPasswordException("비밀번호가 맞지 않습니다.");

        return member;
    }

    //로그아웃
    public void logout(HttpSession session) {
        if(session.getAttribute("member") != null) {
            session.removeAttribute("member");
            session.invalidate();
        }
    }

    //회원 탈퇴
    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Member ID를 찾을 수 없습니다.");
        });
    }

    public List<MyBookmarkDto> myBookmark(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        return bookmarkRepository.findAllByMemberOrderByCreateDateDesc(member).stream()
                .map(bookmark -> MyBookmarkDto.createMyBookmarkDto(bookmark.getArticle()))
                .collect(Collectors.toList());
    }
}
