package com.example.publicwifi.db.dao;


import com.example.publicwifi.db.annotation.Column;
import com.example.publicwifi.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "WIFI_INFO_TB")
public class WifiInfoTb {

    private double distance;

    @Column(name = "MGR_NO")
    private String managementNo;
    @Column(name = "WRDOFC")
    private String wardOffice;
    @Column(name = "MAIN_NM")
    private String mainNumber;
    @Column(name = "ADRES1")
    private String address1;
    @Column(name = "ADRES2")
    private String address2;
    @Column(name = "INSTL_FLOOR")
    private String installFloor;
    @Column(name = "INSTL_TY")
    private String installType;
    @Column(name = "INSTL_MBY")
    private String installMainBody;
    @Column(name = "SVC_SE")
    private String service;
    @Column(name = "CMCWR")
    private String communicationNetwork;
    @Column(name = "CNSTC_YEAR")
    private String constructionYear;
    @Column(name = "INOUT_DOOR")
    private String inOutDoor;
    @Column(name = "REMARS3")
    private String wifiConnectionSituation;
    @Column(name = "LNT")
    private float lnt;
    @Column(name = "LAT")
    private float lat;
    @Column(name = "WORK_DTTM")
    private String workDttm;
}
