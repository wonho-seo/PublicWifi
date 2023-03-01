package com.example.publicwifi.db.repository;

import com.example.publicwifi.db.DataReader;
import com.example.publicwifi.db.dao.BookmarkTb;
import com.example.publicwifi.dto.BookmarkResponseDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkRepository extends DataReader {
    public void writeBookmark(BookmarkTb bookmarkTb) throws SQLException {
        String sql = "INSERT INTO BOOKMARK_TB VALUES (NULL, ?, ?, ?);";

        PreparedStatement prep = getConnection().prepareStatement(sql);
        prep.setString(1, bookmarkTb.getCrateTime());
        prep.setString(2, bookmarkTb.getWifiInfoTbManagedNumber());
        prep.setString(3, bookmarkTb.getBookmarkGroupName());

        prep.executeUpdate();

        prep.close();
    }

    public void deleteBookmarkByGroupName(String bookmarkName) throws SQLException{
        String sql = "DELETE from BOOKMARK_TB where BOOKMARK_GROUP_BM_NM = ?";
        PreparedStatement prep = getConnection().prepareStatement(sql);
        prep.setString(1, bookmarkName);

        prep.executeUpdate();

        prep.close();
    }


    public void deleteBookmark(int key) throws SQLException{
        String sql = "DELETE from BOOKMARK_TB where KEY = ?";
        PreparedStatement prep = getConnection().prepareStatement(sql);
        prep.setInt(1, key);

        prep.executeUpdate();

        prep.close();
    }

    public List<BookmarkResponseDto> findJoin() throws Exception{
        String sql = "SELECT a.KEY, a.C_DDTM, b.MAIN_NM, a.BOOKMARK_GROUP_BM_NM from BOOKMARK_TB AS a INNER JOIN WIFI_INFO_TB AS b ON a.WIFI_INFO_TB_MGR_NO=b.MGR_NO";

        PreparedStatement prep = getConnection().prepareStatement(sql);
        ResultSet row = prep.executeQuery();

        List<BookmarkResponseDto> list = new ArrayList<>();
        while (row.next()){
            BookmarkResponseDto tmp = new BookmarkResponseDto();
            tmp.setId(Integer.parseInt(row.getString("KEY")));
            tmp.setCreateTime(row.getString("C_DDTM"));
            tmp.setBookmarkName(row.getString("BOOKMARK_GROUP_BM_NM"));
            tmp.setWifiName(row.getString("MAIN_NM"));

            list.add(tmp);
        }

        return list;
    }
}
