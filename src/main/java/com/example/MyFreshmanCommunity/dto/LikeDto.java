package com.example.MyFreshmanCommunity.dto;

import com.example.MyFreshmanCommunity.entity.LikeComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private String message;
    private boolean status;
    
    public static LikeDto createLikeDto(String message, LikeComment likeComment) {
        return new LikeDto(
                message,
                likeComment.isStatus()
        );
    }
}
