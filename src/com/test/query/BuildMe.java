package com.test.query;

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
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class BuildMe {
    public static void main(String[] args) throws ClassNotFoundException,
            SQLException {

        long START = 0;
        long END = 0;

        // =====================================================================
        // read table from sql server, DatabaseName is xxx
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

        // Book-Writer��Դʵ���ģ�ʵ������
        TopicIF occurenceBW = builder.makeTopic();
        builder.makeTopicName(occurenceBW, "0cc-BW");

        // temp topic
        // TopicIF bookTopic = null;
        // TopicIF writerTopic = null;
        // TopicIF companyTopic = null;

        ArrayList<TopicIF> bookTopicList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> writerTopicList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> companyTopicList = new ArrayList<TopicIF>();

        // =====================================================================
        //
        // =====================================================================

        ArrayList<String> BookSList = new ArrayList<String>();
        ArrayList<String> WriterSList = new ArrayList<String>();
        ArrayList<String> CompanySList = new ArrayList<String>();

        ArrayList<Row> RowList = new ArrayList<Row>();

        int tempBookIndex = 0;
        int tempWriterIndex = 0;
        int tempCompanyIndex = 0;

        String tempBookString = null;
        String tempWriterString = null;
        String tempCompanyString = null;

        int count = 0;

        START = System.currentTimeMillis();

        while (result.next()) {
            // result.getXXX("OOO") ��ȡ����OOO�ֶε�ֵ��XXXΪOOO�ֶε�����
            /*System.out.println(result.getInt("ID") + "--"
                    + result.getString("Book") + "--"
                    + result.getString("Writer") + "--"
                    + result.getString("Company") + "--"
                    + result.getString("Path"));*/
            // System.out.println("=========================");

            tempBookString = result.getString("Book");
            tempWriterString = result.getString("Writer");
            tempCompanyString = result.getString("Company");

            if (!BookSList.contains(tempBookString)) {
                BookSList.add(tempBookString);
            }

            if (!WriterSList.contains(tempWriterString)) {
                WriterSList.add(tempWriterString);
            }

            if (!CompanySList.contains(tempCompanyString)) {
                CompanySList.add(tempCompanyString);
            }

            tempBookIndex = BookSList.indexOf(tempBookString);
            tempWriterIndex = WriterSList.indexOf(tempWriterString);
            tempCompanyIndex = CompanySList.indexOf(tempCompanyString);

            // System.out.println(BookSList);
            // System.out.println(WriterSList);
            // System.out.println(CompanySList);

            // System.out.println(tempBookIndex);
            // System.out.println(tempWriterIndex);
            // System.out.println(tempCompanyIndex);

            Book resultBook = new Book(tempBookString, tempWriterIndex);
            Writer resultWriter = new Writer(tempWriterString, tempBookIndex,
                    tempCompanyIndex);
            Company resultCompany = new Company(tempCompanyString,
                    tempWriterIndex);
            Row resultRow = new Row(resultBook, resultWriter, resultCompany);
            RowList.add(resultRow);

            count++;
        }

        END = System.currentTimeMillis();
        System.out.println("while (result.next()) Time Cost: " + (END - START)
                + "ms (" + END + "-" + "" + START + ")\n");

        System.out.println("count = " + count);
        System.out.println("BookSList.size() = " + BookSList.size());
        System.out.println("WriterSList.size() = " + WriterSList.size());
        System.out.println("CompanySList.size() = " + CompanySList.size());

        for (Row rr : RowList) {
            // tempBookString = rr.getBook().getString();
            tempWriterString = rr.getWriter().getString();
            tempBookIndex = rr.getWriter().getBookIndex();
            tempCompanyIndex = rr.getWriter().getCompanyIndex();

            // tempCompanyString = rr.getCompany().getString();

            // w�����������У��򴴽�w���⣬w-bʵ����w-c������Ȼ���������ɾ��w
            // w�����������У����ҵ�֮ǰͬ����w��������w���⣬����w-bʵ����w-c����
            if (WriterSList.contains(tempWriterString)) {

                // writer topic
                TopicIF writerTopic = builder.makeTopic();
                builder.makeTopicName(writerTopic, tempCompanyString);

                // name topic
                TopicIF bookTopic = builder.makeTopic();
                builder.makeTopicName(bookTopic, BookSList.get(tempBookIndex));
                // ����occurrence
                builder.makeOccurrence(writerTopic, occurenceBW, tempBookString);

                // TODO WARNING�� ��Ҫ�ж�CompanyTopic�Ƿ����
                // company topic
                TopicIF companyTopic = builder.makeTopic();
                builder.makeTopicName(companyTopic,
                        CompanySList.get(tempCompanyIndex));
                // ����assocation
                AssociationIF associationWC = builder
                        .makeAssociation(topicEmployment);
                builder.makeAssociationRole(associationWC, topicEmployee,
                        writerTopic);
                builder.makeAssociationRole(associationWC, topicEmployer,
                        companyTopic);

                WriterSList.remove(WriterSList.indexOf(rr.getWriter()
                        .getString()));
            } else {
                // TODO
            }
        }
        /*
        // ����occurrence
        builder.makeOccurrence(writerTopic, occurenceNW, nameString);

        // ����assocation
        AssociationIF associationWC = builder
               .makeAssociation(topicEmployment);
        builder.makeAssociationRole(associationWC, topicEmployee,
               writerTopic);
        builder.makeAssociationRole(associationWC, topicEmployer,
               companyTopic);
        */

        /*
        System.err.println("Imported (id " + topicmap.getObjectId() + ").");
        System.err.println("size = " + topicmap.getTopics().size());

        // writer tm to XTM
        new XTMTopicMapWriter("dbtest.xtm").write(topicmap);

        // import XTM into database
        String xtmfile = "dbtest.xtm";
        String propfile = "db.xxx.props";
        TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile);

        // Get the new topic map object
        TopicMapIF tm = rdbmsSrore.getTopicMap();

        // Import the XTM document into the topic map object
        TopicMapImporterIF reader = new XTMTopicMapReader(new Book(xtmfile));
        reader.importInto(tm);
        System.err.println("Imported (id " + tm.getObjectId() + ").");
        System.err.println("size = " + tm.getTopics().size());
        */

    }
}
