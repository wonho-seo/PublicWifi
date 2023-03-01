package com.example.publicwifi.openAPI;

import com.example.publicwifi.db.DataReader;
import com.example.publicwifi.db.repository.WifiInfoRepository;
import com.example.publicwifi.openAPI.dto.TbPublicWifiInfo;
import com.example.publicwifi.openAPI.dto.WifiInfo;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeoulPublicWifi {
    private final WifiInfoRepository wifiInfoRepository = new WifiInfoRepository();
    private final String url = "http://openapi.seoul.go.kr:8088/5568726358736f723536734642754f/json/TbPublicWifiInfo/";
    private final long maxRange = 1000;

    public long getTbPublicWifiInfo() throws SQLException {
        long start = 0;
        Gson gson = new Gson();
        String json = getJson(start, 1);
        WifiInfo wifiInfo = gson.fromJson(json, WifiInfo.class);
        long max = wifiInfo.getTbPublicWifiInfo().getListTotalCount();
        wifiInfoRepository.deleteWifiInfo();

        while (start <= max) {
            json = getJson(start, start + maxRange - 1);
            wifiInfo = gson.fromJson(json, WifiInfo.class);

            wifiInfoRepository.writeWifiInfo(wifiInfo.getTbPublicWifiInfo().getRow());

            start = start + maxRange;
        }

        return max;
    }

    public String getJson(long start, long end) {
        try {
            String url = rangeUrl(start, end);
            Request.Builder builder = new Request.Builder().url(url).get();
            Request request = builder.build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                String str = body.string();
                return str;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String rangeUrl(long start, long end) {
        return this.url + start + "/" + end;
    }
}
