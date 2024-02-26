package com.example.MyFreshmanCommunity.dto;

import com.example.MyFreshmanCommunity.entity.Major;
import com.example.MyFreshmanCommunity.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberInfoDto {
    private Long id;
    private String memberName;
    private String studentId;
    private Major major;


    public static MemberInfoDto createMemberDto(Member member){
        return new MemberInfoDto(
                member.getId(),
                member.getMemberName(),
                member.getStudentId(),
                member.getMajor()
        );
    }
}
