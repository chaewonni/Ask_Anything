package com.example.MyFreshmanCommunity.dto;

import com.example.MyFreshmanCommunity.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDto {
    private String message;
    private boolean status;

    public static BookmarkDto createBookmarkDto(String message, Bookmark bookmark) {
        return new BookmarkDto(
                message,
                bookmark.isStatus()
        );
    }
}
