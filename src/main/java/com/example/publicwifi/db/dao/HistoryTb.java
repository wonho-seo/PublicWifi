package com.example.publicwifi.db.dao;

import com.example.publicwifi.db.annotation.Column;
import com.example.publicwifi.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Table(name = "HISTORY_TB")
public class HistoryTb {

    @Column(name = "KEY")
    private int key;

    @Column(name = "LNT")
    private float lnt;

    @Column(name = "LAT")
    private float lat;

    @Column(name = "A_DTTM")
    private String aDttm;

    public HistoryTb(){
    }
}
