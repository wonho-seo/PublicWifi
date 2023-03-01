package com.example.publicwifi.openAPI.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TbPublicWifiInfo {

    @SerializedName("list_total_count")
    @Expose
    private long listTotalCount;
    @SerializedName("RESULT")
    @Expose
    private Result result;
    @SerializedName("row")
    @Expose
    private List<Row> row;

    @Getter
    @Setter
    public class Result {

        @SerializedName("CODE")
        @Expose
        private String code;
        @SerializedName("MESSAGE")
        @Expose
        private String message;
    }

    @Getter
    @Setter
    public class Row {
        @SerializedName("X_SWIFI_MGR_NO")
        @Expose
        private String xSwifiMgrNo;
        @SerializedName("X_SWIFI_WRDOFC")
        @Expose
        private String xSwifiWrdofc;
        @SerializedName("X_SWIFI_MAIN_NM")
        @Expose
        private String xSwifiMainNm;
        @SerializedName("X_SWIFI_ADRES1")
        @Expose
        private String xSwifiAdres1;
        @SerializedName("X_SWIFI_ADRES2")
        @Expose
        private String xSwifiAdres2;
        @SerializedName("X_SWIFI_INSTL_FLOOR")
        @Expose
        private String xSwifiInstlFloor;
        @SerializedName("X_SWIFI_INSTL_TY")
        @Expose
        private String xSwifiInstlTy;
        @SerializedName("X_SWIFI_INSTL_MBY")
        @Expose
        private String xSwifiInstlMby;
        @SerializedName("X_SWIFI_SVC_SE")
        @Expose
        private String xSwifiSvcSe;
        @SerializedName("X_SWIFI_CMCWR")
        @Expose
        private String xSwifiCmcwr;
        @SerializedName("X_SWIFI_CNSTC_YEAR")
        @Expose
        private String xSwifiCnstcYear;
        @SerializedName("X_SWIFI_INOUT_DOOR")
        @Expose
        private String xSwifiInoutDoor;
        @SerializedName("X_SWIFI_REMARS3")
        @Expose
        private String xSwifiRemars3;
        @SerializedName("LAT")
        @Expose
        private String lat;
        @SerializedName("LNT")
        @Expose
        private String lnt;
        @SerializedName("WORK_DTTM")
        @Expose
        private String workDttm;
    }
}
