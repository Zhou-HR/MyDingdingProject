package com.gdiot.jdbc;

import com.gdiot.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 记录定时任务日志
 *
 * @author ZhouHR
 */
public class JdbcSchedularLog {
    private static Connection conn = null;
    private static PreparedStatement stmt1 = null;
    private static ResultSet rs1 = null;

    public static Connection getConnection() throws Exception {
        if (conn != null) {
            return conn;
        }
        String driverClassName = PropertiesUtil.getValue("mysql.driver");
        String url = PropertiesUtil.getValue("mysql.url");
        String user = PropertiesUtil.getValue("mysql.username");
        String password = PropertiesUtil.getValue("mysql.password");

        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static int insert(String name, String msg, long duration, int count) {

        long start = System.currentTimeMillis();
        try {
            String sql = "insert into ding_schedular_log (name,msg,duration,op_count) values(?,?,?,?) ";
            conn = getConnection();
            stmt1 = conn.prepareStatement(sql);
            stmt1.setString(1, name);
            stmt1.setString(2, msg);
            stmt1.setLong(3, duration);
            stmt1.setLong(4, count);
            stmt1.executeUpdate();
            stmt1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //PerformanceUtil.spendTime(start);
        return 1;

    }


    public static void main(String[] args) throws Exception {

        insert("test", "success", 10L, 4);

    }
}
