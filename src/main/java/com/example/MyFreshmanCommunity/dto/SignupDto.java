package com.example.MyFreshmanCommunity.dto;

import com.example.MyFreshmanCommunity.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private Long id;
    private String memberName;
    private String studentId;
    private String email;
    private String password;
    private String majorName;

    public void encodingPassword(PasswordEncoder passwordEncoder) {
        if (StringUtils.isEmpty(password)) {
            return;
        }
        password = passwordEncoder.encode(password);
    }

}
