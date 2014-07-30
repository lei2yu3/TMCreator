package com.test.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;

// ������Ԫ
class Cell {

    private String string;

    // private int index;

    public Cell() {
        string = null;
        // index = 0;
    }

    public Cell(String ss) {
        setString(ss);
        // setIndex(0);
    }

    // get&set String
    public String getString() {
        return string;
    }

    private void setString(String s) {
        string = s;
    }

    // get&set Index
    // public int getIndex() {
    // return index;
    // }
    //
    // private void setIndex(int n) {
    // index = n;
    // }
}

// ID ����Writer����
class ID extends Cell {

    private int WriterIndex;

    public ID() {
        super();
        WriterIndex = 0;
    }

    public ID(String ss, int ii) {
        super(ss);
        setWriterIndex(ii);
    }

    // get&set WriterIndex
    public int getWriterIndex() {
        return WriterIndex;
    }

    private void setWriterIndex(int i) {
        WriterIndex = i;
    }
}

// Writer ����ID��Company����
class Writer extends Cell {

    private int IDIndex;
    private int CompanyIndex;

    public Writer() {
        super();
        IDIndex = 0;
        CompanyIndex = 0;
    }

    public Writer(String ss, int ii, int jj) {
        super(ss);
        setIDIndex(ii);
        setCompanyIndex(jj);
    }

    // get&set IDIndex
    public int getIDIndex() {
        return IDIndex;
    }

    private void setIDIndex(int i) {
        IDIndex = i;
    }

    // get&set CompanyIndex
    public int getCompanyIndex() {
        return CompanyIndex;
    }

    private void setCompanyIndex(int i) {
        CompanyIndex = i;
    }
}

// Company ����Writer����
class Company extends Cell {

    private int WriterIndex;

    public Company() {
        super();
        WriterIndex = 0;
    }

    public Company(String ss, int ii) {
        super(ss);
        setWriterIndex(ii);
    }

    // get&set WriterIndex
    public int getWriterIndex() {
        return WriterIndex;
    }

    private void setWriterIndex(int i) {
        WriterIndex = i;
    }
}

// ÿ�а��������ֶΣ�ID��Writer��Company
class Row {

    private ID rowID;// = new ID();
    private Writer rowWriter;// = new Writer();
    private Company rowCompany;// = new Company();

    public Row() {
        rowID = new ID();
        rowWriter = new Writer();
        rowCompany = new Company();
    }

    //
    public Row(ID i, Writer w, Company c) {
        rowID = new ID(i.getString(), i.getWriterIndex());
        rowWriter = new Writer(w.getString(), w.getIDIndex(),
                w.getCompanyIndex());
        rowCompany = new Company(c.getString(), c.getWriterIndex());
    }

    public ID getID() {
        return rowID;
    }

    // private void setID(ID ii) {
    // rowID = ii;
    // }

    public Writer getWriter() {
        return rowWriter;
    }

    // private void setWriter(Writer ww) {
    // rowWriter = ww;
    // }

    public Company getCompany() {
        return rowCompany;
    }

    // private void setCompany(Company cc) {
    // rowCompany = cc;
    // }
}

public class Hehe {

    // ID <-- Writer <--> Company
    // ID��Writer��Occurrence��Writer��Company��һ��Association
    // static String[][] HahaArray = { { "aaa", "MMM", "xx1" },
    // { "bbb", "MMM", "yy1" }, { "aaa", "NNN", "xx1" },
    // { "ccc", "NNN", "xx1" }, { "bbb", "OOO", "yy1" },
    // { "ccc", "NNN", "zz1" } };

    static String[][] HahaArray = { { "0", "eee", "HHH" },
            { "1", "aaa", "UUU" }, { "2", "sss", "JJJ" },
            { "3", "ddd", "MMM" }, { "4", "sss", "NNN" },
            { "5", "xxx", "KKK" }, { "6", "www", "HHH" },
            { "7", "qqq", "HHH" }, { "8", "ddd", "NNN" },
            { "9", "eee", "YYY" }, { "10", "aaa", "MMM" },
            { "11", "ddd", "YYY" }, { "12", "www", "NNN" },
            { "13", "www", "MMM" }, { "14", "eee", "MMM" },
            { "15", "sss", "MMM" } };

    // static List<ID> IDList = new ArrayList<ID>();
    // static List<Writer> WriterList = new ArrayList<Writer>();
    // static List<Company> CompanyList = new ArrayList<Company>();

    public static void main(String[] args) throws IOException {

        //
        ArrayList<String> IDSList = new ArrayList<String>();
        ArrayList<String> WriterSList = new ArrayList<String>();
        ArrayList<String> CompanySList = new ArrayList<String>();

        ArrayList<Row> RowList = new ArrayList<Row>();

        int tempIDIndex = 0;
        int tempWriterIndex = 0;
        int tempCompanyIndex = 0;

        String tempIDString = null;
        String tempWriterString = null;
        String tempCompanyString = null;

        /* 
        String propfile = "db.xxx.props";
        TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile);
        TopicMapIF tm = rdbmsSrore.getTopicMap();
        TopicMapBuilderIF builder = tm.getBuilder();

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
        */

        /*
        ID ri = new ID("idxxx", 11);
        Writer rw = new Writer("writerxxx", 22, 33);
        Company rc = new Company("companyxxx", 44);
        Row rr = new Row(ri, rw, rc);

        System.out.println(rr.getID().getString() + " | getWriterString: "
                + rr.getID().getWriterIndex()); //
        System.out.println(rr.getID().getWriterIndex());

        System.out.println("============================================");

        System.out.println(rr.getWriter().getString() + " | getIDIndex: "
                + rr.getWriter().getIDIndex() + " | getCompanyIndex: "
                + rr.getWriter().getCompanyIndex()); //
        System.out.println(rr.getWriter().getIDIndex()); //
        System.out.println(rr.getWriter().getCompanyIndex()); 

        System.out.println("============================================");

        System.out.println(rr.getCompany().getString() + " | getIDIndex: "
                + rr.getCompany().getWriterIndex()); //
        System.out.println(rr.getCompany().getWriterIndex());
        System.out.println("############################################");

        ID rii = new ID();
        Writer rww = new Writer();
        Company rcc = new Company();
        Row rrr = new Row(rii, rww, rcc);

        System.out.println(rrr.getID().getString() + " | getWriterIndex: "
                + rrr.getID().getWriterIndex()); //
        System.out.println(rrr.getID().getWriterIndex());

        System.out.println("============================================");

        System.out.println(rrr.getWriter().getString() + " | getIDIndex: "
                + rrr.getWriter().getIDIndex() + " | getCompanyIndex: "
                + rrr.getWriter().getCompanyIndex()); //
        System.out.println(rrr.getWriter().getIDIndex()); //
        System.out.println(rrr.getWriter().getCompanyIndex());

        System.out.println("============================================");

        System.out.println(rrr.getCompany().getString() + " | getIDIndex: "
                + rrr.getCompany().getWriterIndex()); //
        System.out.println(rrr.getCompany().getWriterIndex());

        System.out.println("############################################");
        */

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        for (int i = 0; i < HahaArray.length; i++) {
            // for (int j = 0; j < HahaArray[i].length; j++) {

            // System.out.println(HahaArray[i][1]);

            if (!IDSList.contains(HahaArray[i][0])) {
                IDSList.add(HahaArray[i][0]);
            }

            if (!WriterSList.contains(HahaArray[i][1])) {
                WriterSList.add(HahaArray[i][1]);
            }

            if (!CompanySList.contains(HahaArray[i][2])) {
                CompanySList.add(HahaArray[i][2]);
            }

            System.out.println(IDSList);
            System.out.println(WriterSList);
            System.out.println(CompanySList);

            System.out.println(IDSList.indexOf(HahaArray[i][0]));
            System.out.println(WriterSList.indexOf(HahaArray[i][1]));
            System.out.println(CompanySList.indexOf(HahaArray[i][2]));

            tempIDIndex = IDSList.indexOf(HahaArray[i][0]);
            tempWriterIndex = WriterSList.indexOf(HahaArray[i][1]);
            tempCompanyIndex = CompanySList.indexOf(HahaArray[i][2]);

            ID haID = new ID(HahaArray[i][0], tempWriterIndex);
            Writer haWriter = new Writer(HahaArray[i][1], tempIDIndex,
                    tempCompanyIndex);
            Company haCompany = new Company(HahaArray[i][2], tempWriterIndex);
            Row haRow = new Row(haID, haWriter, haCompany);
            RowList.add(haRow);
            System.out.println(",.,.,.,.,.,.,.,");

            // RowList.add(new Row(new ID(HahaArray[i][0], WriterSList
            // .indexOf(HahaArray[i][1])), new Writer(HahaArray[i][1],
            // IDSList.indexOf(HahaArray[i][0]), CompanySList
            // .indexOf(HahaArray[i][2])), new Company(
            // HahaArray[i][2], WriterSList.indexOf(HahaArray[i][1]))));
            // }
        }

        for (Row rxx : RowList) {

            System.out.println("ID: " + rxx.getID().getString()
                    + " | getWriterIndex: " + rxx.getID().getWriterIndex());

            System.out.println("Writer: " + rxx.getWriter().getString()
                    + " | getIDIndex: " + rxx.getWriter().getIDIndex()
                    + " | getCompanyIndex: "
                    + rxx.getWriter().getCompanyIndex());

            System.out.println("Company: " + rxx.getCompany().getString()
                    + " | getIDIndex: " + rxx.getCompany().getWriterIndex());
            System.out.println("============================================");

            tempIDString = rxx.getID().getString();
            tempWriterString = rxx.getWriter().getString();
            tempCompanyString = rxx.getCompany().getString();


            /*
            TopicIF idTopic = builder.makeTopic();
            builder.makeTopicName(idTopic, rxx.getID().getString());
            
            TopicIF writerTopic = builder.makeTopic();
            builder.makeTopicName(writerTopic, rxx.getWriter().getString());
            
            TopicIF companyTopic = builder.makeTopic();
            builder.makeTopicName(companyTopic, rxx.getCompany().getString());

            // ����occurrence
            builder.makeOccurrence(writerTopic, occurenceNW, rxx.getID().getString());

            // ����assocation
            AssociationIF associationWC = builder
                    .makeAssociation(topicEmployment);
            builder.makeAssociationRole(associationWC, topicEmployee,
                    writerTopic);
            builder.makeAssociationRole(associationWC, topicEmployer,
                    companyTopic);
            */
        }

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        /*
        // ɾ�������е��ظ���1
        int[] nums = { 5, 6, 6, 6, 8, 8, 7, 9, 4, 3, 3, 2, 2, 2, 2, 1 };
        List<Integer> numList = new ArrayList<Integer>();
        for (int i : nums)
            numList.add(i);
        Set<Integer> numSet = new HashSet<Integer>();
        numSet.addAll(numList);
        System.out.println(numSet);

        // ɾ�������е��ظ���2
        String[] s = { "7", "1", "2", "3", "5", "3", "5", "1", "1", "4" };

        List<String> list = Arrays.asList(s);
        System.out.println(list);
        System.out.println(list.indexOf("1")); // indexOf() ���ص�һ�γ��ֵ�λ�ã�1���ڶ���
        System.out.println(list.lastIndexOf("1")); // lastIndexOf()
                                                   // �������һ�γ��ֵ�λ�ã�8���ھţ�

        List<String> l = new ArrayList<String>();
        for (String a : list) {
            if (!l.contains(a)) {// contains() ������ĳԪ�ط���true�����򷵻�false
                l.add(a);// �����������
            }
        }
        System.out.println(l);
        */

        // ��������
        // try {
        // String queryInput = (new BufferedReader(new
        // InputStreamReader(System.in))).readLine();
        // System.out.println("hah= " + queryInput);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        //
    }
}


/*

����Ŀ�����һ�����



�������Ŀ����Ϊoccurrence�������������ַ���occurrence�Լ�������ʵ����topic




import "http://psi.ontopia.net/tolog/string/" as str

select $Occurrence, $OccurrenceType, $OccurrenceValue, $Topic, $TopicName from

occurrence($Topic, $Occurrence), 
type($Occurrence,$OccurrenceType),
{resource($Occurrence,$OccurrenceValue) | 
value($Occurrence,$OccurrenceValue)},
str:contains($OccurrenceValue, "Puccini"),

topic-name($Topic, $name), 
value($name, $TopicName)

order by $Occurrence?

�������Ŀ����Ϊassociation������ù����Ĺ�����ɫ








import "http://psi.ontopia.net/tolog/string/" as str

select $RoleTopic1, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 from

association($Association),
type($Association, $AssociationType),
topic-name($AssociationType, $AssociationName),
value($AssociationName, $AssociationString), 
str:contains($AssociationString, "Completed by"),

role-player($role1, $RoleTopic1),
association-role($Association, $role1),
association-role($Association, $role2),
role-player($role2, $RoleTopic2),
$RoleTopic1 /= $RoleTopic2,

type($role1, $RoleType1),
type($role2, $RoleType2)

order by $Association?

�������Ŀ����Ϊtopic�����������Ĺ��������Լ�ʵ���������������



��صĹ���������

import "http://psi.ontopia.net/tolog/string/" as str
select $Topic, $TopicName, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 from

topic-name($Topic, $name), 
value($name, $TopicName),
str:contains($TopicName, "Puccini"),

role-player($role1, $Topic),
association-role($Association, $role1),
association-role($Association, $role2),
role-player($role2, $RoleTopic2),
$Topic /= $RoleTopic2,

type($role1, $RoleType1),
type($role2, $RoleType2),
type($Association, $AssociationType)

order by $Topic?




������ʵ��

import "http://psi.ontopia.net/tolog/string/" as str

select $Topic, $TopicName, $Occurrence, $OccurrenceType, $OccurrenceValue from

topic-name($Topic, $name), 
value($name, $TopicName),
str:contains($TopicName, "Puccini"),

occurrence($Topic, $Occurrence), 
type($Occurrence,$OccurrenceType),
{resource($Occurrence,$OccurrenceValue) | 
value($Occurrence,$OccurrenceValue)}


order by $Topic?






���ȥ��select����$TopicName������������ʾ������topicname


 

 */
