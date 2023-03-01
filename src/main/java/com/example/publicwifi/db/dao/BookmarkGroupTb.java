package com.example.publicwifi.db.dao;

import com.example.publicwifi.db.annotation.Column;
import com.example.publicwifi.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "BOOKMARK_GROUP_TB")
public class BookmarkGroupTb {

    @Column(name = "KEY")
    private int key;
    @Column(name = "BM_NM")
    private String bookmarkName;
    @Column(name = "RANK_NUMBER")
    private int order;
    @Column(name = "C_DDTM")
    private String createTime;
    @Column(name = "M_DDTM")
    private String modifiedTime;
}
