package com.example.publicwifi.openAPI.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WifiInfo {
    @SerializedName("TbPublicWifiInfo")
    @Expose
    private TbPublicWifiInfo tbPublicWifiInfo;
}
