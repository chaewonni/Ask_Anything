package com.example.MyFreshmanCommunity.repository;

import com.example.MyFreshmanCommunity.entity.Major;
import com.example.MyFreshmanCommunity.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByMemberName(String memberName);

    Member findByEmail(String email);

    Long countByEmail(String email);
}
