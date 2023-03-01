package com.example.publicwifi.db.repository;

import com.example.publicwifi.db.DataReader;
import com.example.publicwifi.db.dao.BookmarkGroupTb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BookmarkGroupRepository extends DataReader {
    public int writeBookmarkGroup(BookmarkGroupTb groupTb) throws SQLException, Exception{
        List<BookmarkGroupTb> list = (List<BookmarkGroupTb>) find(BookmarkGroupTb.class);

        int order = Math.min(list.size() + 1, groupTb.getOrder());
        String sql = "INSERT INTO BOOKMARK_GROUP_TB VALUES (NULL, ?, ?, ?, ?) RETURNING KEY;";
        PreparedStatement prep = getConnection().prepareStatement(sql);
        prep.setString(1, groupTb.getBookmarkName());
        prep.setInt(2, order);
        prep.setString(3, groupTb.getCreateTime());
        prep.setString(4, groupTb.getModifiedTime());

        ResultSet row = prep.executeQuery();
        int key = 0;
        while(row.next()){
            key = Integer.parseInt(row.getString("KEY"));
        }

        prep.close();

        sql = "update BOOKMARK_GROUP_TB set RANK_NUMBER = RANK_NUMBER + 1 where RANK_NUMBER >= ? AND not KEY = ?;";
        prep = getConnection().prepareStatement(sql);
        prep.setInt(1, order);
        prep.setInt(2, key);

        prep.executeUpdate();

        prep.close();


        return key;
    }

    public String deleteBookmarkGroup(int order) throws SQLException {
        String sql = "DELETE from BOOKMARK_GROUP_TB where RANK_NUMBER = ? RETURNING BM_NM";


        PreparedStatement prep = getConnection().prepareStatement(sql);

        prep.setInt(1, order);

        ResultSet row= prep.executeQuery();

        String deleteName = "";

        while (row.next()){
            deleteName = row.getString("BM_NM");
        }
        prep.close();

        sql = "update BOOKMARK_GROUP_TB set RANK_NUMBER = RANK_NUMBER - 1 where RANK_NUMBER > ?;";

        prep = getConnection().prepareStatement(sql);

        prep.setInt(1, order);

        prep.executeUpdate();

        prep.close();

        return deleteName;
    }


    public void updateBookMarkGroupByName(int key, int nowOrder, String bookmarkGroupName, int order) throws SQLException, Exception {
        List<BookmarkGroupTb> list = (List<BookmarkGroupTb>) find(BookmarkGroupTb.class);
        order = Math.min(list.size(), order);

        String sql = "update BOOKMARK_GROUP_TB set RANK_NUMBER = ?, BM_NM = ?, M_DDTM = ? where key = ?;";

        PreparedStatement prep = getConnection().prepareStatement(sql);
        prep.setInt(1, order);
        prep.setString(2, bookmarkGroupName);
        prep.setString(3, LocalDateTime.now().toString());
        prep.setInt(4, key);

        prep.executeUpdate();


        prep.close();

        if (nowOrder == order) {
            return;
        }

        sql = "update BOOKMARK_GROUP_TB set RANK_NUMBER = RANK_NUMBER + ? where RANK_NUMBER >= ? AND RANK_NUMBER <= ? AND NOT KEY = ?;";
        prep = getConnection().prepareStatement(sql);

        prep.setInt(1, nowOrder > order ? 1 : -1);
        prep.setInt(2, Math.min(nowOrder, order));
        prep.setInt(3, Math.max(nowOrder, order));
        prep.setInt(4, key);

        prep.executeUpdate();
        prep.close();
    }
}
