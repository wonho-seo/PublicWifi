package com.example.publicwifi;

import com.example.publicwifi.db.DataReader;
import com.example.publicwifi.db.dao.HistoryTb;
import com.example.publicwifi.db.dao.WifiInfoTb;
import com.example.publicwifi.db.repository.HistoryRepository;
import com.example.publicwifi.db.repository.WifiInfoRepository;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "find-wifi", value = "/find-wifi")
public class FindWifi extends HttpServlet {
    private final HistoryRepository historyRepository = new HistoryRepository();
    private final WifiInfoRepository wifiInfoRepository = new WifiInfoRepository();
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        float lnt = Float.parseFloat(request.getParameter("lnt"));
        float lat = Float.parseFloat(request.getParameter("lat"));

        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {

            List<WifiInfoTb> list = (List<WifiInfoTb>) wifiInfoRepository.find(WifiInfoTb.class);
            list.sort((a, b) -> (int)((getDistance(lat, lnt, a.getLnt(), a.getLat())
                    - getDistance(lat, lnt, b.getLnt(), b.getLat())) * 10000000));
            List<WifiInfoTb> answer = new ArrayList<>();
            for (int i = 0; i < 20; i++){
                WifiInfoTb tmp = list.get(i);
                tmp.setDistance(Math.round(getDistance(lat, lnt, tmp.getLnt(), tmp.getLat()) * 10000) / 10000.0);

                answer.add(tmp);
            }

            response.getWriter().append(gson.toJson(answer));
            saveHistory(lat,lnt);

        } catch (Exception e){
            response.setStatus(500);
            e.printStackTrace();
        }
    }
    private void saveHistory(float lat, float lnt) throws SQLException{
        HistoryTb historyTb = new HistoryTb();
        historyTb.setLat(lat);
        historyTb.setLnt(lnt);
        historyTb.setADttm(LocalDateTime.now().toString());

        historyRepository.writeHistory(historyTb);
    }

    private double getDistance(float lat, float lnt, float lat2, float lnt2){
        double theta = lnt - lnt2;
        double dist = Math.sin(deg2rad(lat))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist / 1000;
    }

    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }
}
