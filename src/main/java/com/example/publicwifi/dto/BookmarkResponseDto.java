package com.example.publicwifi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkResponseDto {
    private int id;

    private String bookmarkName;

    private String wifiName;

    private String createTime;
}
