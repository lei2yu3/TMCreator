package com.test.query;

import java.io.IOException;
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
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

// build the topic map
public class BuildMe {
    public static void main(String[] args) throws ClassNotFoundException,
            SQLException, IOException {

        // time counting
        long START = 0;
        long END = 0;

        // =====================================================================
        // read table from sql server
        // =====================================================================

        START = System.currentTimeMillis();

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=xxx"; // 连接服务器和数据库xxx
        String userName = "sa"; // 用户名
        String userPwd = "123123"; // 密码
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null; // set of the query result

        Class.forName(driverName);
        connection = DriverManager.getConnection(dbURL, userName, userPwd);
        System.out.println("Connection Successful!");

        statement = connection.createStatement();
        // String query =
        // "SELECT TOP 13 [ID], [Book] ,[Writer] ,[Company] ,[Path] FROM [TestTopicMap].[dbo].[TopicMap]";
        String query = "select * from TestTopicMap.dbo.TopicMap order by ID";
        // query = "select * from xxx.dbo.test order by ID";
        System.out.println(query);

        result = statement.executeQuery(query);

        END = System.currentTimeMillis();
        System.out.println("read Database Time Cost: " + (END - START) + "ms ("
                + END + "-" + "" + START + ")\n");

        // =====================================================================
        // topic map
        // =====================================================================

        // 创建InMemory TM
        TopicMapStoreIF inmemoryStore = new InMemoryTopicMapStore();
        TopicMapIF topicmap = inmemoryStore.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();

        // Writer-Comany关联的，关联类型，关联角色类型
        TopicIF topicEmployment = builder.makeTopic();
        builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        builder.makeTopicName(topicEmployer, "Employer");

        // System.out.println(topicEmployment.getTopicNames().toString());
        // System.out.println(topicEmployee.getTopicNames().toString());
        // System.out.println(topicEmployer.getTopicNames().toString());

        // Book-Writer资源实例的，实例类型
        TopicIF occurenceBW = builder.makeTopic();
        builder.makeTopicName(occurenceBW, "occBW");

        // =====================================================================
        // 
        // =====================================================================

        // Row list 从数据库中读取的每一条记录为一个Row
        ArrayList<Row> RowList = new ArrayList<Row>();

        // string list - Row中三个字段分别取出不重复的部分放入list
        ArrayList<String> bookSList = new ArrayList<String>();
        ArrayList<String> writerSList = new ArrayList<String>();
        ArrayList<String> companySList = new ArrayList<String>();

        // topic list - 根据上述字符生成主题放入list，两个list一一对应
        ArrayList<TopicIF> bookTopicList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> writerTopicList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> companyTopicList = new ArrayList<TopicIF>();

        // temporary variable
        int tempBookIndex = 0;
        int tempWriterIndex = 0;
        int tempCompanyIndex = 0;

        String tempBookString = null;
        String tempWriterString = null;
        String tempCompanyString = null;

        // int count = 0;

        // =====================================================================
        // make topic&string lists from sql query result
        // =====================================================================

        START = System.currentTimeMillis();

        // traverse result
        while (result.next()) {
            // result.getXXX("OOO") 获取搜索结果表中OOO字段的值，XXX为OOO字段的类型
            /*
            System.out.println(result.getInt("ID") + "--" + result.getString("Name") + "--"
                    + result.getString("Writer") + "--" + result.getString("Company") + "--"
                    + result.getString("Path"));
            */
            // System.out.println("=========================");

            // string is not null
            tempBookString = result.getString("Name") == null ? "null" : result
                    .getString("Name");
            // tempBookString = result.getString("Book") == null ? "null" :
            // result.getString("Book");
            tempWriterString = result.getString("Writer") == null ? "null"
                    : result.getString("Writer");
            tempCompanyString = result.getString("Company") == null ? "null"
                    : result.getString("Company");

            // 若result中字段的值不在string list中则加入，并创建相关的主题加入topic list
            if (!bookSList.contains(tempBookString)) {
                
                bookSList.add(tempBookString);

                TopicIF bookTopic = builder.makeTopic();
                builder.makeTopicName(bookTopic, tempBookString);

                bookTopicList.add(bookTopic);
            }

            if (!writerSList.contains(tempWriterString)) {
                
                writerSList.add(tempWriterString);

                TopicIF writerTopic = builder.makeTopic();
                builder.makeTopicName(writerTopic, tempWriterString);

                writerTopicList.add(writerTopic);
            }

            if (!companySList.contains(tempCompanyString)) {
                
                companySList.add(tempCompanyString);

                TopicIF companyTopic = builder.makeTopic();
                builder.makeTopicName(companyTopic, tempCompanyString);

                companyTopicList.add(companyTopic);
            }

            // System.out.println(bookSList);
            // System.out.println(writerSList);
            // System.out.println(companySList);

            // System.out.println(tempBookIndex);
            // System.out.println(tempWriterIndex);
            // System.out.println(tempCompanyIndex);

            // 某字段在string list中的index
            tempBookIndex = bookSList.indexOf(tempBookString);
            tempWriterIndex = writerSList.indexOf(tempWriterString);
            tempCompanyIndex = companySList.indexOf(tempCompanyString);

            // create Row list
            Book resultBook = new Book(tempBookString, tempWriterIndex);
            Writer resultWriter = new Writer(tempWriterString, tempBookIndex,
                    tempCompanyIndex);
            Company resultCompany = new Company(tempCompanyString,
                    tempWriterIndex);
            Row resultRow = new Row(resultBook, resultWriter, resultCompany);
            RowList.add(resultRow);

            // count++;
        }

        END = System.currentTimeMillis();
        System.out.println("while (result.next()) Time Cost: " + (END - START)
                + "ms (" + END + "-" + "" + START + ")\n");

        // System.out.println("count = " + count);
        System.out.println("bookSList.size() = " + bookSList.size());
        System.out.println("writerSList.size() = " + writerSList.size());
        System.out.println("companySList.size() = " + companySList.size());

        System.out.println("bookTopicList.size() = " + bookTopicList.size());
        System.out
                .println("writerTopicList.size() = " + writerTopicList.size());
        System.out.println("companyTopicList.size() = "
                + companyTopicList.size());

        // traverse Row list, make Occurrence & Association
        for (Row rr : RowList) {

            // 通过Row分别获取与writer相关的book和company的index
            tempWriterString = rr.getWriter().getString();
            tempBookIndex = rr.getWriter().getBookIndex();
            tempCompanyIndex = rr.getWriter().getCompanyIndex();

            // 若writer字段在writer string list中
            if (writerSList.contains(tempWriterString)) {

                // 获取writer字段在string list中的index，然后定位到topic list中对应的topic
                tempWriterIndex = writerSList.indexOf(tempWriterString);

                // get recent topic
                // writerTopicList.get(tempWriterIndex);
                // bookTopicList.get(tempBookIndex);
                // companyTopicList.get(tempCompanyIndex);

                // 根据各自的index分别获取writer topic和book string创建occurrence
                builder.makeOccurrence(writerTopicList.get(tempWriterIndex),
                        occurenceBW, bookSList.get(tempBookIndex));

                // 根据各自的index分别获取writer topic和company topic创建association
                AssociationIF associationEmployment = builder
                        .makeAssociation(topicEmployment);
                builder.makeAssociationRole(associationEmployment,
                        topicEmployee, writerTopicList.get(tempWriterIndex));
                builder.makeAssociationRole(associationEmployment,
                        topicEmployer, companyTopicList.get(tempCompanyIndex));

            }
        }

        System.err.println("Imported (id " + topicmap.getObjectId() + ").");
        System.err.println("size = " + topicmap.getTopics().size());

        // writer topic map to XTM
        new XTMTopicMapWriter("HeheTest.xtm").write(topicmap);
        /*
        // import XTM into database
        String xtmfile = "dbtest.xtm";
        String propfile = "db.xxx.props";
        TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile);

        // get new topic map object
        TopicMapIF tm = rdbmsSrore.getTopicMap();

        // import XTM document into topic map object
        TopicMapImporterIF reader = new XTMTopicMapReader(new Book(xtmfile));
        reader.importInto(tm);
        System.err.println("Imported (id " + tm.getObjectId() + ").");
        System.err.println("size = " + tm.getTopics().size());
        */

    }
}
