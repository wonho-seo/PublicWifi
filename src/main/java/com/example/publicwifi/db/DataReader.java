package com.example.publicwifi.db;

import com.example.publicwifi.db.annotation.Column;
import com.example.publicwifi.db.annotation.Table;
import org.sqlite.SQLiteConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    private Connection connection;
    private String dbFileName = "C:\\Users\\sjdlr\\IdeaProjects\\PublicWifi\\wifiDB.db";
    private boolean isOpened = false;


    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataReader() {
        open();
    }

    public boolean open() {
        try {
            // 읽기 전용
            SQLiteConfig config = new SQLiteConfig();
            config.setBusyTimeout(100);
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbFileName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        isOpened = true;
        return true;
    }


    public boolean close() {
        if (this.isOpened == false) {
            return true;
        }

        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Connection getConnection() {
        return this.connection;
    }


    public Object find(Class c) throws SQLException, Exception {
        if (this.isOpened == false) {
            return null;
        }

        String tableName = getTableName(c);

        String sql = "SELECT * FROM " + tableName;
        PreparedStatement prep = this.connection.prepareStatement(sql);
        ResultSet row = prep.executeQuery();
        Constructor cConstructor = c.getConstructor();
        List<Object> result = new ArrayList<>();

        while (row.next()) {
            Object col = cConstructor.newInstance();
            for (Field cField : c.getDeclaredFields()) {
                String columnName = cField.getName();
                Method method = c.getMethod("set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1),
                        new Class[]{cField.getType()});

                Column annotation = cField.getAnnotation(Column.class);
                if (annotation == null)
                    continue;
                columnName = annotation.name();


                Object[] field = null;
                if (cField.getType().equals(int.class)) {
                    field = new Object[]{Integer.parseInt(row.getString(columnName))};
                } else if (cField.getType().equals(String.class)) {
                    field = new Object[]{row.getString(columnName)};
                } else if (cField.getType().equals(float.class)) {
                    field = new Object[]{Float.parseFloat(row.getString(columnName))};
                }
                if (field == null) {
                    throw new SQLDataException();
                }

                method.invoke(col, field);
            }
            result.add(col);
        }

        row.close();
        prep.close();
        return result;
    }

    public Object findById(Class c, int key) throws SQLException, Exception {
        if (this.isOpened == false) {
            return null;
        }

        String tableName = getTableName(c);

        String sql = "SELECT * FROM " + tableName + "WHERE KEY = key";
        PreparedStatement prep = this.connection.prepareStatement(sql);
        ResultSet row = prep.executeQuery();
        Constructor cConstructor = c.getConstructor();
        List<Object> result = new ArrayList<>();

        while (row.next()) {
            Object col = cConstructor.newInstance();
            for (Field cField : c.getDeclaredFields()) {
                String columnName = cField.getName();
                Method method = c.getMethod("set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1),
                        new Class[]{cField.getType()});

                Column annotation = cField.getAnnotation(Column.class);
                if (annotation == null)
                    continue;
                columnName = annotation.name();


                Object[] field = null;
                if (cField.getType().equals(int.class)) {
                    field = new Object[]{Integer.parseInt(row.getString(columnName))};
                } else if (cField.getType().equals(String.class)) {
                    field = new Object[]{row.getString(columnName)};
                } else if (cField.getType().equals(float.class)) {
                    field = new Object[]{Float.parseFloat(row.getString(columnName))};
                }
                if (field == null) {
                    throw new SQLDataException();
                }

                method.invoke(col, field);
            }
            result.add(col);
        }

        row.close();
        prep.close();
        return result;
    }

    private String getTableName(Class c) {
        String tableName = c.getName();
        tableName = tableName.substring(tableName.lastIndexOf('.') + 1);

        Table tableAnnotation = (Table) c.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            tableName = tableAnnotation.name();
        }

        return tableName;
    }
}
