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

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=xxx"; // 连接服务器和数据库test
        String userName = "sa"; // 用户名
        String userPwd = "123123"; // 密码
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null; // set of the query result

        // =====================================================================
        // topic map
        // =====================================================================

        // 筛选不重复的字段保存为topic
        // ArrayList<TopicIF> nameList = new ArrayList<TopicIF>();
        // ArrayList<TopicIF> writerList = new ArrayList<TopicIF>();
        // ArrayList<TopicIF> companyList = new ArrayList<TopicIF>();

        // topic name string
        String nameString;// = null;
        String writerString;// = null;
        String companyString;// = null;

        // 创建InMemory TM
        // TopicMapStoreIF inmemoryStore = new InMemoryTopicMapStore();
        // TopicMapIF topicmap = inmemoryStore.getTopicMap();
        // TopicMapBuilderIF builder = topicmap.getBuilder();
        try {
            // 创建RDBMS TM
            String propfile = "db.xxx.props";
            TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile);
            TopicMapIF tm = rdbmsSrore.getTopicMap();
            TopicMapBuilderIF builder = tm.getBuilder();

            // temp topic
            TopicIF nameTopic;
            TopicIF writerTopic = null;
            TopicIF companyTopic = null;

            // 添加Writer-Comany关联
            TopicIF topicEmployment = builder.makeTopic();
            builder.makeTopicName(topicEmployment, "Employment");

            TopicIF topicEmployee = builder.makeTopic();
            builder.makeTopicName(topicEmployee, "Employee");

            TopicIF topicEmployer = builder.makeTopic();
            builder.makeTopicName(topicEmployer, "Employer");

            // 添加Name-Writer资源实例
            TopicIF occurenceNW = builder.makeTopic();
            builder.makeTopicName(occurenceNW, "0ccNW");

            //
            // ArrayList<Resource> resourceList = new ArrayList<Resource>();

            long START, END;
            // =====================================================================
            // 读取数据库信息创建TM
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
            // 按行输出查询结果，并创建TM
            while (result.next()) {
                // result.getXXX("OOO") 获取表中OOO字段的值，XXX为OOO字段的类型
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

                // 为字段name创建topic
                // nameTopic = builder.makeTopic();
                // builder.makeTopicName(nameTopic, nameString);
                // nameList.add(nameTopic);

                // 为字段writer创建topic
                writerTopic = builder.makeTopic();
                builder.makeTopicName(writerTopic, writerString);
                // writerList.add(writerTopic);

                // 为字段company创建topic
                companyTopic = builder.makeTopic();
                builder.makeTopicName(companyTopic, companyString);
                // companyList.add(companyTopic);

                // 创建occurrence
                builder.makeOccurrence(writerTopic, occurenceNW, nameString);

                // 创建assocation
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