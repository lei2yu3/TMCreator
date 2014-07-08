package com.test.rdbms;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicMapWriterIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapReader;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class DBBuilder {

    public static void main(String[] argv) {

        // =====================================================================
        // data base
        // =====================================================================

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=xxx"; // ���ӷ����������ݿ�test
        String userName = "sa"; // �û���
        String userPwd = "123123"; // ����
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null; // set of the query result

        // =====================================================================
        // topic map
        // =====================================================================

        // ɸѡ���ظ����ֶα���Ϊtopic
        // ArrayList<TopicIF> nameList = new ArrayList<TopicIF>();
        // ArrayList<TopicIF> writerList = new ArrayList<TopicIF>();
        // ArrayList<TopicIF> companyList = new ArrayList<TopicIF>();

        // topic name string
        String nameString;// = null;
        String writerString;// = null;
        String companyString;// = null;

        // ����InMemory TM
        // TopicMapStoreIF inmemoryStore = new InMemoryTopicMapStore();
        // TopicMapIF topicmap = inmemoryStore.getTopicMap();
        // TopicMapBuilderIF builder = topicmap.getBuilder();
        try {
            // ����RDBMS TM
            String propfile = "db.xxx.props";
            TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile);
            TopicMapIF tm = rdbmsSrore.getTopicMap();
            TopicMapBuilderIF builder = tm.getBuilder();

            // temp topic
            TopicIF nameTopic;
            TopicIF writerTopic = null;
            TopicIF companyTopic = null;

            // ���Writer-Comany����
            TopicIF topicEmployment = builder.makeTopic();
            builder.makeTopicName(topicEmployment, "Employment");

            TopicIF topicEmployee = builder.makeTopic();
            builder.makeTopicName(topicEmployee, "Employee");

            TopicIF topicEmployer = builder.makeTopic();
            builder.makeTopicName(topicEmployer, "Employer");

            // ���Name-Writer��Դʵ��
            TopicIF occurenceNW = builder.makeTopic();
            builder.makeTopicName(occurenceNW, "0ccNW");

            //
            // ArrayList<Resource> resourceList = new ArrayList<Resource>();

            long START, END;
            // =====================================================================
            // ��ȡ���ݿ���Ϣ����TM
            // =====================================================================

            // try {

            // ----------------------------------------------------------
            // read database
            // ----------------------------------------------------------
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("Connection Successful!");

            statement = connection.createStatement();
            String query = "select * from TestTopicMap.dbo.TopicMap order by ID";
            // query = "select * from xxx.dbo.test order by ID";
            System.out.println(query);
            // statement.executeUpdate(query);
            result = statement.executeQuery(query);

            System.out.println("=========================");
            START = System.currentTimeMillis();
            // ���������ѯ�����������TM
            while (result.next()) {
                // result.getXXX("OOO") ��ȡ����OOO�ֶε�ֵ��XXXΪOOO�ֶε�����
                Resource res = new Resource(result.getInt("ID"),
                        result.getString("Name"), result.getString("Writer"),
                        result.getString("Company"), result.getString("Path"));

                // res.resourcePrint();
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
                // nameTopic = builder.makeTopic();
                // builder.makeTopicName(nameTopic, nameString);
                // nameList.add(nameTopic);

                // Ϊ�ֶ�writer����topic
                writerTopic = builder.makeTopic();
                builder.makeTopicName(writerTopic, writerString);
                // writerList.add(writerTopic);

                // Ϊ�ֶ�company����topic
                companyTopic = builder.makeTopic();
                builder.makeTopicName(companyTopic, companyString);
                // companyList.add(companyTopic);

                // ����occurrence
                builder.makeOccurrence(writerTopic, occurenceNW, nameString);

                // ����assocation
                AssociationIF associationWC = builder
                        .makeAssociation(topicEmployment);
                builder.makeAssociationRole(associationWC, topicEmployee,
                        writerTopic);
                builder.makeAssociationRole(associationWC, topicEmployer,
                        companyTopic);

            }

            END = System.currentTimeMillis();
            System.out.println("Create Time Cost: " + (END - START) + "ms ("
                    + END + "-" + "" + START + ")\n");
            System.out.println("=========================");

            // System.err.println("nameList.size() =  " + nameList.size());
            // System.err.println("companyList.size() =  " +
            // companyList.size());
            // System.err.println("writerList.size() =  " + writerList.size());

            // System.err.println("Imported (id " + topicmap.getObjectId() +
            // ").");
            // System.err.println("size = " + topicmap.getTopics().size());
            // System.err.println("Imported (id " + tm.getObjectId() + ").");
            // System.err.println("size = " + tm.getTopics().size());

            // writer tm to XTM
            new XTMTopicMapWriter("dbtest.xtm").write(tm);

            // import XTM into database
            String xtmfile = "dbtest.xtm";
            // String propfile = "db.xxx.props";
            // TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile);
            // TopicMapIF tm = rdbmsSrore.getTopicMap();

            // Import the XTM document into the topic map object
            TopicMapImporterIF reader = new XTMTopicMapReader(new File(xtmfile));
            reader.importInto(tm);
            System.err.println("Imported (id " + tm.getObjectId() + ").");
            System.err.println("size = " + tm.getTopics().size());

            System.out.println("=========================");

            // query
            QueryWrapper wrapper = new QueryWrapper(tm);
            // QueryWrapper wrapper = new QueryWrapper(topicmap);

            String sss = "association($ASSOC)?";
            sss = "topic($topic)?";
            // sss = "topic-name(id75, $topic)?";
            // System.out.println(wrapper.queryForMaps(sss).size());

            START = System.currentTimeMillis();

            @SuppressWarnings("unchecked")
            List list = wrapper.queryForList(sss);

            END = System.currentTimeMillis();
            System.out.println("Query Time Cost: " + (END - START) + "ms ("
                    + END + "-" + "" + START + ")\n");

            for (int q = 0; q < list.size(); q++) {

                // System.out.println(list.get(q));
            }
            System.out.println("size = " + list.size());

            System.out.println("closeeeeeeeeeeeeeeeeeeee");

            // ----------------------------------------------------------
            // close
            // ----------------------------------------------------------

            // inmemoryStore.close();

            result.close();
            statement.close();
            connection.close();

            rdbmsSrore.commit();
            rdbmsSrore.close();

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
}