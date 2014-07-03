package com.malloc.rdbms;

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
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.Association;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Hah {

    public static void main(String[] argv) {

        // =====================================================================
        // data base
        // =====================================================================
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=TestTopicMap"; // 连接服务器和数据库test
        String userName = "sa"; // 用户名
        String userPwd = "123123"; // 密码
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null; // set of the query result

        // =====================================================================
        // topic map
        // =====================================================================

        // 筛选不重复的字段保存为topic
        ArrayList<TopicIF> nameList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> writerList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> companyList = new ArrayList<TopicIF>();

        // topic name string
        String nameString;// = null;
        String writerString;// = null;
        String companyString;// = null;

        // 创建InMemory TM
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

        // 添加Writer-Comany关联
        TopicIF topicEmployment = builder.makeTopic();
        builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        builder.makeTopicName(topicEmployer, "Employer");

        AssociationIF associationWC = builder.makeAssociation(topicEmployment);

        // 添加Name-Writer资源实例
        TopicIF occurenceNW = builder.makeTopic();
        builder.makeTopicName(occurenceNW, "0ccNW");

        //
        ArrayList<Resource> resourceList = new ArrayList<Resource>();

        // =====================================================================
        // 读取数据库信息创建TM
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

            // 按行输出查询结果，并创建TM
            while (result.next()) {
                // result.getXXX("OOO") 获取表中OOO字段的值，XXX为OOO字段的类型
                Resource res = new Resource(result.getInt("ID"),
                        result.getString("Name"), result.getString("Writer"),
                        result.getString("Company"), result.getString("Path"));

                res.resourcePrint();
                resourceList.add(res);

                // ----------------------------------------------------------
                // create tm
                // ----------------------------------------------------------

                // nameString = result.getString("Name");
                // writerString = result.getString("Writer");
                // companyString = result.getString("Company");
                // nameString = result.getString("Name") == null ? "null" :
                // result
                // .getString("Name");
                // writerString = result.getString("Writer") == null ? "null"
                // : result.getString("Writer");
                // companyString = result.getString("Company") == null ? "null"
                // : result.getString("Company");
                //
                // // 为字段name创建topic
                // nameTopic = builder.makeTopic();
                // builder.makeTopicName(nameTopic, nameString);
                // nameList.add(nameTopic);

                // 筛选writer字段，对不重复的部分，创建topic
                // int wlSize = writerList.size();
                // for (int i = 0; i < wlSize; i++) {
                // System.out.println("wlSize = " + wlSize);
                // System.out.println("writerList.size() = " +
                // writerList.size());
                // if (wlSize == 0){
                //
                // writerTopic = builder.makeTopic();
                // builder.makeTopicName(writerTopic, writerString);
                //
                // writerList.add(writerTopic);
                // }
                //
                // if (writerString == writerList.get(i).toString()) {
                // writerTopic = writerList.get(i);
                // } else {
                // writerTopic = builder.makeTopic();
                // builder.makeTopicName(writerTopic, writerString);
                //
                // writerList.add(writerTopic);
                // }
                // }

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

                // // 筛选company字段，对不重复的部分，创建topic
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
                // // 创建occurrence
                // builder.makeOccurrence(writerTopic, occurenceNW, nameString);
                //
                // // 创建assocation
                // builder.makeAssociationRole(associationWC, topicEmployee,
                // writerTopic);
                // builder.makeAssociationRole(associationWC, topicEmployer,
                // companyTopic);

            }

            // for (Resource rr : resourceList) {
            // nameString = rr.getName() == null ? "null" : rr.getName();
            // writerString = rr.getWriter() == null ? "null" : rr.getName();
            // companyString = rr.getCompany() == null ? "null" : rr.getName();
            // }

            System.out.println("=========================");

            int i, j, k;
            for (i = 0; i < resourceList.size(); i++) {
                nameString = resourceList.get(i).getName() == null ? "null"
                        : resourceList.get(i).getName();
                writerString = resourceList.get(i).getWriter() == null ? "null"
                        : resourceList.get(i).getWriter();
                companyString = resourceList.get(i).getCompany() == null ? "null"
                        : resourceList.get(i).getCompany();

                // name
//                nameTopic = builder.makeTopic();
//                builder.makeTopicName(nameTopic, nameString);
//                nameList.add(nameTopic);

                // writer
                for (j = 0; j < i; j++) {
                    if (writerString.equals(resourceList.get(j).getWriter())) {
                        writerTopic = writerList.get(j);
                        break;
                    } else {
                    }
                }
                if (i == j) {
                    writerTopic = builder.makeTopic();
                    builder.makeTopicName(writerTopic, writerString);
                    writerList.add(writerTopic);
                    System.out.println("writerString = " + writerString);
                }

                // company
                for (k = 0; k < i; k++) {
                    if (companyString.equals(resourceList.get(k).getCompany())) {
                        companyTopic = companyList.get(k);
                        break;
                    } else {
                    }
                }
                if (i == k) {
                    companyTopic = builder.makeTopic();
                    builder.makeTopicName(companyTopic, companyString);
                    companyList.add(companyTopic);
                    System.out.println("companyString = " + companyString);
                }

                // 创建occurrence
                builder.makeOccurrence(writerTopic, occurenceNW, nameString);

                // 创建assocation
                builder.makeAssociationRole(associationWC, topicEmployee,
                        writerTopic);
                builder.makeAssociationRole(associationWC, topicEmployer,
                        companyTopic);

            }

            System.err.println("nameList.size() =  " + nameList.size());
            System.err.println("companyList.size() =  " + companyList.size());
            System.err.println("writerList.size() =  " + writerList.size());
            System.err.println("Imported (id " + topicmap.getObjectId() + ").");
            System.err.println("size = " + topicmap.getTopics().size());

            // writer tm to XTM
            new XTMTopicMapWriter("dbtest.xtm").write(topicmap);

            // import XTM into database
            String xtmfile = "dbtest.xtm";
            String propfile = "db.testsqlserver.props";
            TopicMapStoreIF store1 = new RDBMSTopicMapStore(propfile);

            // Get the new topic map object
            TopicMapIF tm = store1.getTopicMap();
            
            // Import the XTM document into the topic map object
            TopicMapImporterIF reader = new XTMTopicMapReader(new File(xtmfile));
            reader.importInto(tm);
            System.err.println("Imported (id " + tm.getObjectId() + ").");
            System.err.println("size = " + tm.getTopics().size() );

            
            // query
            QueryWrapper wrapper = new QueryWrapper(tm);
            String sss = "association($ASSOC)?";
            sss = "topic($topic)?";
            sss = "topic-name(id75, $topic)?";
            System.out.println(wrapper.queryForMaps(sss).size());
            @SuppressWarnings("unchecked")
            List list = wrapper.queryForList(sss);

            for (int q = 0; q < list.size(); q++) {

                System.out.println( list.get(q));
            }
            System.out.println("size = " + list.size());
            

            store1.commit();
            store1.close();
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