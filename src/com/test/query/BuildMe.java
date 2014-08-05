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

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=xxx"; // ���ӷ����������ݿ�xxx
        String userName = "sa"; // �û���
        String userPwd = "123123"; // ����
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

        // ����InMemory TM
        TopicMapStoreIF inmemoryStore = new InMemoryTopicMapStore();
        TopicMapIF topicmap = inmemoryStore.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();

        // Writer-Comany�����ģ��������ͣ�������ɫ����
        TopicIF topicEmployment = builder.makeTopic();
        builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        builder.makeTopicName(topicEmployer, "Employer");

        // System.out.println(topicEmployment.getTopicNames().toString());
        // System.out.println(topicEmployee.getTopicNames().toString());
        // System.out.println(topicEmployer.getTopicNames().toString());

        // Book-Writer��Դʵ���ģ�ʵ������
        TopicIF occurenceBW = builder.makeTopic();
        builder.makeTopicName(occurenceBW, "occBW");

        // =====================================================================
        // 
        // =====================================================================

        // Row list �����ݿ��ж�ȡ��ÿһ����¼Ϊһ��Row
        ArrayList<Row> RowList = new ArrayList<Row>();

        // string list - Row�������ֶηֱ�ȡ�����ظ��Ĳ��ַ���list
        ArrayList<String> bookSList = new ArrayList<String>();
        ArrayList<String> writerSList = new ArrayList<String>();
        ArrayList<String> companySList = new ArrayList<String>();

        // topic list - ���������ַ������������list������listһһ��Ӧ
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
            // result.getXXX("OOO") ��ȡ�����������OOO�ֶε�ֵ��XXXΪOOO�ֶε�����
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

            // ��result���ֶε�ֵ����string list������룬��������ص��������topic list
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

            // ĳ�ֶ���string list�е�index
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

            // ͨ��Row�ֱ��ȡ��writer��ص�book��company��index
            tempWriterString = rr.getWriter().getString();
            tempBookIndex = rr.getWriter().getBookIndex();
            tempCompanyIndex = rr.getWriter().getCompanyIndex();

            // ��writer�ֶ���writer string list��
            if (writerSList.contains(tempWriterString)) {

                // ��ȡwriter�ֶ���string list�е�index��Ȼ��λ��topic list�ж�Ӧ��topic
                tempWriterIndex = writerSList.indexOf(tempWriterString);

                // get recent topic
                // writerTopicList.get(tempWriterIndex);
                // bookTopicList.get(tempBookIndex);
                // companyTopicList.get(tempCompanyIndex);

                // ���ݸ��Ե�index�ֱ��ȡwriter topic��book string����occurrence
                builder.makeOccurrence(writerTopicList.get(tempWriterIndex),
                        occurenceBW, bookSList.get(tempBookIndex));

                // ���ݸ��Ե�index�ֱ��ȡwriter topic��company topic����association
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
