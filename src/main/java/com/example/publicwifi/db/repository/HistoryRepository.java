package com.example.publicwifi.db.repository;

import com.example.publicwifi.db.DataReader;
import com.example.publicwifi.db.dao.HistoryTb;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoryRepository extends DataReader {
    public int deleteHistory(int key) {
        String sql = "DELETE from HISTORY_TB where KEY = ?";
        try {
            PreparedStatement prep = getConnection().prepareStatement(sql);

            prep.setInt(1, key);
            int deleteKey = prep.executeUpdate();

            prep.close();
            return deleteKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int writeHistory(HistoryTb historyTb) throws SQLException {
        String sql = "INSERT INTO HISTORY_TB VALUES (NULL, ?, ?, ?);";
        PreparedStatement prep = getConnection().prepareStatement(sql);

        prep.setFloat(1, historyTb.getLnt());
        prep.setFloat(2, historyTb.getLat());
        prep.setString(3, historyTb.getADttm().toString());

        int key = prep.executeUpdate();

        prep.close();

        return key;
    }
}
