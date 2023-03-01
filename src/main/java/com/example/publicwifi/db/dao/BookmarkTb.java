package com.example.publicwifi.db.dao;

import com.example.publicwifi.db.annotation.Column;
import com.example.publicwifi.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "BOOKMARK_TB")
public class BookmarkTb {
    @Column(name = "KEY")
    private int key;

    @Column(name = "C_DDTM")
    private String crateTime;

    @Column(name = "WIFI_INFO_TB_MGR_NO")
    private String wifiInfoTbManagedNumber;

    @Column(name = "BOOKMARK_GROUP_BM_NM")
    private String bookmarkGroupName;
}
