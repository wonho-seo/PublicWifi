package com.example.publicwifi.db.repository;

import com.example.publicwifi.db.DataReader;
import com.example.publicwifi.db.dao.WifiInfoTb;
import com.example.publicwifi.openAPI.dto.TbPublicWifiInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WifiInfoRepository extends DataReader {
    public void writeWifiInfo(List<TbPublicWifiInfo.Row> rows) throws SQLException {
        String sql = "INSERT INTO WIFI_INFO_TB VALUES (NULL, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?);";
        PreparedStatement prep = getConnection().prepareStatement(sql);

        for (TbPublicWifiInfo.Row row : rows){
            prep.setString(1, row.getXSwifiMgrNo());
            prep.setString(2, row.getXSwifiWrdofc());
            prep.setString(3, row.getXSwifiMainNm());
            prep.setString(4, row.getXSwifiAdres1());
            prep.setString(5, row.getXSwifiAdres2());
            prep.setString(6, row.getXSwifiInstlFloor());
            prep.setString(7, row.getXSwifiInstlTy());
            prep.setString(8, row.getXSwifiInstlMby());
            prep.setString(9, row.getXSwifiSvcSe());
            prep.setString(10, row.getXSwifiCmcwr());
            prep.setString(11, row.getXSwifiCnstcYear());
            prep.setString(12, row.getXSwifiInoutDoor());
            prep.setString(13, row.getXSwifiRemars3());
            prep.setFloat(14, Float.parseFloat(row.getLnt()));
            prep.setFloat(15, Float.parseFloat(row.getLat()));
            prep.setString(16, row.getWorkDttm());
            prep.executeUpdate();
        }


        prep.close();
    }


    public int deleteWifiInfo() throws SQLException {
        String sql = "DELETE from WIFI_INFO_TB";
        PreparedStatement prep = getConnection().prepareStatement(sql);

        int deleteKey = prep.executeUpdate();

        prep.close();

        return deleteKey;
    }
}
