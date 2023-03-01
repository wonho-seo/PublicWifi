package com.example.publicwifi;

import com.example.publicwifi.openAPI.SeoulPublicWifi;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "open-api", value = "/open-api")

public class OpenApi extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("utf-8");
        SeoulPublicWifi seoulPublicWifi = new SeoulPublicWifi();
        long count = 0;
        try {
            count = seoulPublicWifi.getTbPublicWifiInfo();
        } catch (SQLException e){
            e.printStackTrace();
        }

        response.getWriter().append(String.valueOf(count));
    }
}
