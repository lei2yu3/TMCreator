package com.malloc.rdbms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Hah {

    public static void main(String[] argv) {

        // =====================================================================
        // data base
        // =====================================================================
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=TestTopicMap"; // ���ӷ����������ݿ�test
        String userName = "sa"; // �û���
        String userPwd = "123123"; // ����
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null; // set of the query result

        // =====================================================================
        // topic map
        // =====================================================================

        // ɸѡ���ظ����ֶα���Ϊtopic
        ArrayList<TopicIF> nameList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> writerList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> companyList = new ArrayList<TopicIF>();

        // topic name string
        String nameString;// = null;
        String writerString;// = null;
        String companyString;// = null;

        // ����InMemory TM
        TopicMapStoreIF store = new InMemoryTopicMapStore();
        TopicMapIF topicmap = store.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();

        // temp topic
        TopicIF nameTopic;
        TopicIF writerTopic = builder.makeTopic();
        builder.makeTopicName(writerTopic, "tempwriter");
        writerList.add(writerTopic);

        TopicIF companyTopic = builder.makeTopic();
        builder.makeTopicName(companyTopic, "tempcompany");
        companyList.add(companyTopic);

        // ���Writer-Comany����
        TopicIF topicEmployment = builder.makeTopic();
        builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        builder.makeTopicName(topicEmployer, "Employer");

        AssociationIF associationWC = builder.makeAssociation(topicEmployment);

        // ���Name-Writer��Դʵ��
        TopicIF occurenceNW = builder.makeTopic();
        builder.makeTopicName(occurenceNW, "NW");

        // =====================================================================
        // ��ȡ���ݿ���Ϣ����TM
        // =====================================================================
        try {

            // ----------------------------------------------------------
            // read database
            // ----------------------------------------------------------
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("Connection Successful!");

            statement = connection.createStatement();
            // String query =
            // "SELECT TOP 13 [ID], [Name] ,[Writer] ,[Company] ,[Path] FROM [TestTopicMap].[dbo].[TopicMap]";
            String query = "select * from TestTopicMap.dbo.TopicMap order by ID desc";
            System.out.println(query);
            // statement.executeUpdate(query);

            long START = System.currentTimeMillis();

            result = statement.executeQuery(query);

            long END = System.currentTimeMillis();
            System.out.println("Time Cost: " + (END - START) + "ms (" + END
                    + "-" + "" + START + ")\n");

            // ���������ѯ�����������TM
            while (result.next()) {
                // result.getXXX("OOO") ��ȡ����OOO�ֶε�ֵ��XXXΪOOO�ֶε�����
                Resource res = new Resource(result.getInt("ID"),
                        result.getString("Name"), result.getString("Writer"),
                        result.getString("Company"), result.getString("Path"));

                res.resourcePrint();
                // resourceList.add(res);

                // ----------------------------------------------------------
                // create tm
                // ----------------------------------------------------------

                // nameString = result.getString("Name");
                // writerString = result.getString("Writer");
                // companyString = result.getString("Company");
                nameString = result.getString("Name") == null ? "null" : result
                        .getString("Name");
                writerString = result.getString("Writer") == null ? "null"
                        : result.getString("Writer");
                companyString = result.getString("Company") == null ? "null"
                        : result.getString("Company");

                // Ϊ�ֶ�name����topic
                nameTopic = builder.makeTopic();
                builder.makeTopicName(nameTopic, nameString);
                nameList.add(nameTopic);

                // ɸѡwriter�ֶΣ��Բ��ظ��Ĳ��֣�����topic
                for (int i = 0; i < writerList.size(); i++) {
                    if (writerString == writerList.get(i).toString()) {
                        writerTopic = writerList.get(i);
                    } else {
                        writerTopic = builder.makeTopic();
                        builder.makeTopicName(writerTopic, writerString);

                        writerList.add(writerTopic);
                    }
                }
                
                // for (TopicIF ww : writerList) {
                // if (writerString == ww.toString()) {
                // writerTopic = ww;
                // } else {
                // writerTopic = builder.makeTopic();
                // builder.makeTopicName(writerTopic, writerString);
                //
                // writerList.add(writerTopic);
                // }
                // }

                // // ɸѡcompany�ֶΣ��Բ��ظ��Ĳ��֣�����topic
                // for (TopicIF cc : companyList) {
                // if (companyString == cc.toString()) {
                // companyTopic = cc;
                // } else {
                // companyTopic = builder.makeTopic();
                // builder.makeTopicName(companyTopic, companyString);
                // writerList.add(companyTopic);
                // }
                // }
                //
                // // ����occurrence
                // builder.makeOccurrence(writerTopic, occurenceNW, nameString);
                //
                // // ����assocation
                // builder.makeAssociationRole(associationWC, topicEmployee,
                // writerTopic);
                // builder.makeAssociationRole(associationWC, topicEmployer,
                // companyTopic);

            }

            System.err.println("Imported (id " + topicmap.getObjectId() + ").");
            System.err.println("size = " + topicmap.getTopics().size());

            new XTMTopicMapWriter("dbtest.xtm").write(topicmap);

            // ----------------------------------------------------------
            // close
            // ----------------------------------------------------------
            store.close();

            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("database loading error");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("connection error");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void tmCreator() {

    }
}