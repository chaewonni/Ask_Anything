package com.example.MyFreshmanCommunity.entity;

import com.example.MyFreshmanCommunity.dto.ArticleDto;
import com.example.MyFreshmanCommunity.repository.MajorRepository;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor //모든 필드를 매개변수로 갖는 생성자 자동 생성
@NoArgsConstructor //매개변수가 아예 없는 기본 생성자 자동 생성
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private int bookmarkCount;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    public static Article createArticle(ArticleDto articleDto, Member member, Major major){
        return new Article(
                null,
                articleDto.getTitle(),
                articleDto.getContent(),
                0,
                LocalDateTime.now(),
                member,
                major
        );
    }

    public void patch(ArticleDto articleDto) { //수정할 내용이 있는 경우에만 동작
        if(articleDto.getTitle() != null)
            this.title = articleDto.getTitle();
        if(articleDto.getContent() != null)
            this.content = articleDto.getContent();
    }
}
