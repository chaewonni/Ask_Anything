package com.example.MyFreshmanCommunity.entity;

import com.example.MyFreshmanCommunity.dto.ArticleDto;
import com.example.MyFreshmanCommunity.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private LocalDateTime createDate;

    @Column
    private int likesCount;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    public static Comment createComment(CommentDto commentDto, Member member, Article article) {
        return new Comment(
                null,
                commentDto.getContent(),
                LocalDateTime.now(),
                0,
                member,
                article
        );
    }

    public void patch(CommentDto commentDto) { //수정할 내용이 있는 경우에만 동작
        if(commentDto.getContent() != null)
            this.content = commentDto.getContent();
    }

}
