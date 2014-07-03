package com.malloc.rdbms;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.OccurrenceIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.entry.TopicMapReferenceIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapSource;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;
import net.ontopia.topicmaps.query.utils.QueryWrapper;

public class dbtest {
    public static void main(String[] argv) throws IOException {

        ArrayList<Resource> resourceList = new ArrayList<Resource>();
/*
// ============================================================
// make topic map from rdbms test BEGIN
        ArrayList<TopicIF> nameList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> writerList = new ArrayList<TopicIF>();
        ArrayList<TopicIF> companyList = new ArrayList<TopicIF>();
        TopicIF tName;
        TopicIF tWriter = null;
        TopicIF tCompany = null;
        String sName;// = result.getString("Name");
        String sWriter;// = null;
        String sCompany;// = null;

        TopicMapStoreIF store = new InMemoryTopicMapStore();
        TopicMapIF topicmap = store.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();

        TopicIF topicEmployment = builder.makeTopic();
        builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        builder.makeTopicName(topicEmployer, "Employer");

        AssociationIF associationWC = builder.makeAssociation(topicEmployment);

        TopicIF occurenceNW = builder.makeTopic();
        builder.makeTopicName(occurenceNW, "NW");
// make topic map from rdbms test END
// ============================================================
*/
        // �������ݿ�
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
        String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=TestTopicMap"; // ���ӷ����������ݿ�test
        String userName = "sa"; // �û���
        String userPwd = "123123"; // ����
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null; // set of the query result

        try {
            // connect to the sql sever
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("Connection Successful!");

            statement = connection.createStatement();
            String query = "SELECT TOP 100 [ID], [Name] ,[Writer] ,[Company] ,[Path] FROM [TestTopicMap].[dbo].[TopicMap]";
            // String query =
            // "select * from TestTopicMap.dbo.TopicMap order by ID desc";
            System.out.println(query);
            // statement.executeUpdate(query);

            long START = System.currentTimeMillis();

            result = statement.executeQuery(query);

            long END = System.currentTimeMillis();
            System.out.println("Time Cost: " + (END - START) + "ms (" + END
                    + "-" + "" + START + ")\n");

            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

            while (result.next()) {
                // result.getXXX("OOO") ��ȡ����OOO�ֶε�ֵ��XXXΪOOO�ֶε�����
                Resource res = new Resource(result.getInt("ID"),
                        result.getString("Name"), result.getString("Writer"),
                        result.getString("Company"), result.getString("Path"));

                //res.resourcePrint();
                resourceList.add(res);
/*
// ============================================================
// make topic map from rdbms test BEGIN
                sName = result.getString("Name");
                sWriter = result.getString("Writer");
                sCompany = result.getString("Company");

                // Ϊ�ֶ�name����topic
                tName = builder.makeTopic();
                builder.makeTopicName(tName, sName);
                nameList.add(tName);

                // ɸѡwriter�ֶΣ��Բ��ظ��Ĳ��֣�����topic
                for (TopicIF ww : writerList) {
                    if (sWriter == ww.toString()) {
                        tWriter = ww;
                    } else {
                        tWriter = builder.makeTopic();
                        builder.makeTopicName(tWriter, sWriter);

                        writerList.add(tWriter);
                    }
                }

                // ɸѡcompany�ֶΣ��Բ��ظ��Ĳ��֣�����topic
                for (TopicIF cc : companyList) {
                    if (sCompany == cc.toString()) {
                        tCompany = cc;
                    } else {
                        tCompany = builder.makeTopic();
                        builder.makeTopicName(tCompany, sCompany);
                        writerList.add(tCompany);
                    }
                }

                // ����occurrence
                builder.makeOccurrence(tWriter, occurenceNW, sName);

                // ����assocation
                builder.makeAssociationRole(associationWC, topicEmployee,
                        tWriter);
                builder.makeAssociationRole(associationWC, topicEmployer,
                        tCompany);
// make topic map from rdbms test END
// ============================================================
*/
            }

            System.out.println("^^^^^^^^^^^^^^^| " + resourceList.size()
                    + " |^^^^^^^^^^^^^^^^");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // ��result��statement��connectionһ��ʼ����null�����׳�SQL�쳣������Ӧ�����ж�һ��
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        
        
        


        /*
        // ��ָ��TM���뵽���ݿ���
        TopicMapStoreIF store = new RDBMSTopicMapStore("db.testsqlserver.props");
        // Get the new topic map object
        TopicMapIF tm = store.getTopicMap();
        
        // Import the XTM document into the topic map object
        TopicMapImporterIF reader = new XTMTopicMapReader(new File(xtmfile));
        reader.importInto(tm);
        System.err.println("Imported (id " + tm.getObjectId() + ").");
        System.err.println("size = " + tm.getTopics().size() );

        // Commit the transaction
        store.commit();
        // Close store (and database connection)
        store.close();
        */
        
        
        
        
        
        
       
        // create TM by RDBMS ��ȡ���ݿ��е�TM
        String propfile = "db.testsqlserver.props";

        RDBMSTopicMapSource source = new RDBMSTopicMapSource();
        source.setPropertyFile(propfile);
        Iterator tms = source.getReferences().iterator();

        while (tms.hasNext()) {
            //System.out.println("hasNext");
            TopicMapReferenceIF ref = null;
            TopicMapStoreIF store = null;

            try {
                ref = (TopicMapReferenceIF) tms.next();
                store = ref.createStore(true);
                TopicMapIF tm = store.getTopicMap();

                System.out.println("Topic Map ID: " + tm.getObjectId());
                System.out.println("  Topics: " + tm.getTopics().size());
                System.out.println("  Associations: "
                        + tm.getAssociations().size());

                QueryWrapper wrapper = new QueryWrapper(tm);
                // System.out.println(wrapper.queryForMaps("association($ASSOC)?").size());

                //String s = "occurrence(puccini, $occ) order by $occ?";
                String s = "topic-name(puccini,$T1)?";
                //String s = "association($a)?";
                //String s = "select $ASSOC, $T2 from role-player($ROLE1, aida), association-role($ASSOC, $ROLE1), association-role($ASSOC, $ROLE2), role-player($ROLE2, $T2), $T2 /= aida?";
                
                List list = wrapper.queryForMaps(s);

                for (int i = 0; i < list.size(); i++) {

                    System.out.println(list.get(i));
                }

                System.out.println(list.size());
                System.out.println("Done.");

                store.close();
            } finally {
                if (store != null)
                    store.close();
                if (ref != null)
                    ref.close();
            }
        }

    }
}
