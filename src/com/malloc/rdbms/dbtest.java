package com.malloc.rdbms;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;

import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMapReferenceIF;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapSource;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;

public class dbtest {
    public static void main(String[] argv) {
        // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=TestTopicMap"; // ���ӷ����������ݿ�test
        String userName = "sa"; // Ĭ���û���
        String userPwd = "123123"; // ����
        Connection dbConn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("Connection Successful!");

            stmt = dbConn.createStatement();
            // String query =
            // "SELECT TOP 1 [FileID], [FileName] ,[BZZ] ,[TJDW] ,[FilePath] FROM [TestTopicMap].[dbo].[TopicMap]";
            String query = "select * from TestTopicMap.dbo.TopicMap where FileID < 3200";
            System.out.println(query);
            // stmt.executeUpdate(query);

            long START = System.currentTimeMillis();

            rs = stmt.executeQuery(query);

            long END = System.currentTimeMillis();
            System.out.println("Time Cost: " + (END - START) + "ms (" + END
                    + "-" + "" + START + ")\n");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            int id;
            int num = 0;
            String a, b, c, d;
            while (rs.next()) {
                id = rs.getInt("FileID");
                a = rs.getString("FileName");
                b = rs.getString("BZZ");
                c = rs.getString("TJDW");
                d = rs.getString("FilePath");
                //System.out.println(id + "==" + a + "==" + b + "==" + c + "==" + d);
                num++;
            }
            System.out.println("^^^^^^^^^^^^^^^|" + num + " |^^^^^^^^^^^^^^^^");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    // ���rsһ��ʼ����null�Ļ���������жϾ�һֱ��SQL�쳣������Ӧ�����ж�һ��
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    // ���stmtһ��ʼ����null�Ļ���������жϾ�һֱ��SQL�쳣������Ӧ�����ж�һ��
                    stmt.close();
                    stmt = null;
                }
                if (dbConn != null) {
                    // ���dbConnһ��ʼ����null�Ļ���������жϾ�һֱ��SQL�쳣������Ӧ�����ж�һ��
                    dbConn.close();
                    dbConn = null;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
        // create TM by RDBMS
        try {
            TopicMapStoreIF store = new RDBMSTopicMapStore();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
