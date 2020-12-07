package com.gdiot.jdbc;

import com.gdiot.util.PerformanceUtil;
import com.gdiot.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * @author ZhouHR
 */
@Slf4j
public class JdbcErpOPRView {
    private static Connection conn = null;
    private static PreparedStatement stmt = null;
    private static ResultSet rs = null;

    private static Connection conn2 = null;
    private static PreparedStatement stmt2 = null;
    private static ResultSet rs2 = null;

    public static Connection getConnection() throws Exception {

        String driverClassName = PropertiesUtil.getValue("erp.driver");
        String url = PropertiesUtil.getValue("erp.url");
        String user = PropertiesUtil.getValue("erp.username");
        String password = PropertiesUtil.getValue("erp.password");
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static Connection getConnection2() throws Exception {
        if (conn2 != null) {
            return conn2;
        }
        String driverClassName = PropertiesUtil.getValue("mysql.driver");
        String url = PropertiesUtil.getValue("mysql.url");
        String user = PropertiesUtil.getValue("mysql.username");
        String password = PropertiesUtil.getValue("mysql.password");
        Class.forName(driverClassName);
        conn2 = DriverManager.getConnection(url, user, password);
        return conn2;
    }

    //	private static Map<String,Long> mapUserCode=new HashMap<String,Long>();
    private static final long FRESH_DAYS = 24L * 3600L * 1000L * 1L;//一天

    public static int selectORPByProjCode(String proj_code, String businessCode, String applyReasonType, String auditResult) {
        long duration = 0;
        String name = "select erp orp";
        String msg = "success";
        int count = 0;

        long start = System.currentTimeMillis();
        try {
            conn = getConnection();
            String sql = "select * from guodong.V_QUERY_ORP " + "where project_no='" + proj_code + "' and REFORM_TYPE LIKE '%" + applyReasonType + "%' ";

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            conn2 = getConnection2();
            String sql1 = "delete from erp_orp where businessCode='" + businessCode + "' ";
            stmt2 = conn2.prepareStatement(sql1);
            stmt2.executeUpdate();

            String sql2 = "insert into erp_orp (PK_ORP ,PK_CORP,ASSET_NO,PROJECT_NO,PROJ_BS_NAME,JZ_TYPE,LON,LAT,PACT_CODE_GD,";
            sql2 += "PACT_TYPE_COST,FEE_TOTAL,FEE_NO_TAX,PACT_TAX_RATE,START_DATE,END_DATE,TOTAL_MONTHS,PERIOD_MONTHS,";
            sql2 += "FEE_PRICE,APPROVE_DATE,FLAG_END_PACT,FLAG_REMEDY,STANDARD_START_DATE,REAL_END_DATE,REMOVE_DATE,";
            sql2 += "CUSTOMER_ENDDATE,WAIT_FOR_REMOVE,PACT_TYPE,REFORM_TYPE,REFORM_DING_NO,CORPCODE,CORPNAME,CITYCODE,CITYNAME,UNITCODE,UNITNAME,";
            sql2 += "businessCode,applyReasonType,auditResult ) ";
            sql2 += "values (?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + " ?, ?, ?, ?, ?, ?, ?, ?, "
                    + " ?, ?, ?, ?, ?, ?, ?, "
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                    + " ?, ?, ?)";
            while (rs.next()) {
                stmt2 = conn2.prepareStatement(sql2);

                for (int i = 1; i < 36; i++) {
                    String str = rs.getString(i);
                    stmt2.setString(i, str);
                }
                stmt2.setString(36, businessCode);
                stmt2.setString(37, applyReasonType);
                stmt2.setString(38, auditResult);
                stmt2.executeUpdate();
                stmt2.close();
                count++;
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            log.error(msg);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                msg = e.getMessage();
            }
            duration = PerformanceUtil.spendTime(start);
            JdbcSchedularLog.insert(name, msg, duration, count);
        }
        return 1;

    }


    public static void main(String args[]) throws Exception {
//		for(int i=0;i<1100;i++)
        selectORPByProjCode("GDJZ2015004512YD", null, null, null);

    }
}
