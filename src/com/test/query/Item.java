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

// base cell
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

    // private void setIndex(int n) {
    // index = n;
    // }
}

// Book keyword includes WriterIndex
class Book extends Cell {

    private int WriterIndex;

    public Book() {
        super();
        WriterIndex = 0;
    }

    public Book(String ss, int ii) {
        super(ss);
        setWriterIndex(ii);
    }

    public int getWriterIndex() {
        return WriterIndex;
    }

    private void setWriterIndex(int i) {
        WriterIndex = i;
    }
}

// Writer keyword includes BookIndex & CompanyIndex
class Writer extends Cell {

    private int BookIndex;
    private int CompanyIndex;

    public Writer() {
        super();
        BookIndex = 0;
        CompanyIndex = 0;
    }

    public Writer(String ss, int ii, int jj) {
        super(ss);
        setBookIndex(ii);
        setCompanyIndex(jj);
    }

    public int getBookIndex() {
        return BookIndex;
    }

    private void setBookIndex(int i) {
        BookIndex = i;
    }

    public int getCompanyIndex() {
        return CompanyIndex;
    }

    private void setCompanyIndex(int i) {
        CompanyIndex = i;
    }
}

// Company keyword includes WriterIndex
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

    public int getWriterIndex() {
        return WriterIndex;
    }

    private void setWriterIndex(int i) {
        WriterIndex = i;
    }
}

// one Row includes there keywords, Book, Writer & Company
class Row {

    private Book rowBook;// = new Book();
    private Writer rowWriter;// = new Writer();
    private Company rowCompany;// = new Company();

    public Row() {
        rowBook = new Book();
        rowWriter = new Writer();
        rowCompany = new Company();
    }

    //
    public Row(Book i, Writer w, Company c) {
        rowBook = new Book(i.getString(), i.getWriterIndex());
        rowWriter = new Writer(w.getString(), w.getBookIndex(),
                w.getCompanyIndex());
        rowCompany = new Company(c.getString(), c.getWriterIndex());
    }

    public Book getBook() {
        return rowBook;
    }

    // private void setBook(Book ii) {
    // rowBook = ii;
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

public class Item {

    // Book <-- Writer <--> Company
    // Book是Writer的Occurrence，Writer和Company是一对Association
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

    // static List<Book> BookList = new ArrayList<Book>();
    // static List<Writer> WriterList = new ArrayList<Writer>();
    // static List<Company> CompanyList = new ArrayList<Company>();

    public static void main(String[] args) throws IOException {

        //
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

        /* 
        String propfile = "db.xxx.props";
        TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile);
        TopicMapIF tm = rdbmsSrore.getTopicMap();
        TopicMapBuilderIF builder = tm.getBuilder();

        // 添加Writer-Comany关联
        TopicIF topicEmployment = builder.makeTopic();
        builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        builder.makeTopicName(topicEmployer, "Employer");

        // 添加Book-Writer资源实例
        TopicIF occurenceNW = builder.makeTopic();
        builder.makeTopicName(occurenceNW, "0ccNW");
        */

        /*
        Book ri = new Book("idxxx", 11);
        Writer rw = new Writer("writerxxx", 22, 33);
        Company rc = new Company("companyxxx", 44);
        Row rr = new Row(ri, rw, rc);

        System.out.println(rr.getBook().getString() + " | getWriterString: "
                + rr.getBook().getWriterIndex()); //
        System.out.println(rr.getBook().getWriterIndex());

        System.out.println("============================================");

        System.out.println(rr.getWriter().getString() + " | getBookIndex: "
                + rr.getWriter().getBookIndex() + " | getCompanyIndex: "
                + rr.getWriter().getCompanyIndex()); //
        System.out.println(rr.getWriter().getBookIndex()); //
        System.out.println(rr.getWriter().getCompanyIndex()); 

        System.out.println("============================================");

        System.out.println(rr.getCompany().getString() + " | getBookIndex: "
                + rr.getCompany().getWriterIndex()); //
        System.out.println(rr.getCompany().getWriterIndex());
        System.out.println("############################################");

        Book rii = new Book();
        Writer rww = new Writer();
        Company rcc = new Company();
        Row rrr = new Row(rii, rww, rcc);

        System.out.println(rrr.getBook().getString() + " | getWriterIndex: "
                + rrr.getBook().getWriterIndex()); //
        System.out.println(rrr.getBook().getWriterIndex());

        System.out.println("============================================");

        System.out.println(rrr.getWriter().getString() + " | getBookIndex: "
                + rrr.getWriter().getBookIndex() + " | getCompanyIndex: "
                + rrr.getWriter().getCompanyIndex()); //
        System.out.println(rrr.getWriter().getBookIndex()); //
        System.out.println(rrr.getWriter().getCompanyIndex());

        System.out.println("============================================");

        System.out.println(rrr.getCompany().getString() + " | getBookIndex: "
                + rrr.getCompany().getWriterIndex()); //
        System.out.println(rrr.getCompany().getWriterIndex());

        System.out.println("############################################");
        */

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        for (int i = 0; i < HahaArray.length; i++) {
            // for (int j = 0; j < HahaArray[i].length; j++) {

            // System.out.println(HahaArray[i][1]);

            tempBookString = HahaArray[i][0];
            tempWriterString = HahaArray[i][1];
            tempCompanyString = HahaArray[i][2];

            if (!BookSList.contains(tempBookString)) {
                BookSList.add(tempBookString);
            }

            if (!WriterSList.contains(tempWriterString)) {
                WriterSList.add(tempWriterString);
            }

            if (!CompanySList.contains(tempCompanyString)) {
                CompanySList.add(tempCompanyString);
            }

            System.out.println(BookSList);
            System.out.println(WriterSList);
            System.out.println(CompanySList);

            System.out.println(BookSList.indexOf(tempBookString));
            // System.out.println(BookSList.lastIndexOf(tempBookString));
            System.out.println(WriterSList.indexOf(tempWriterString));
            // System.out.println(WriterSList.lastIndexOf(tempWriterString));
            System.out.println(CompanySList.indexOf(tempCompanyString));
            // System.out.println(CompanySList.lastIndexOf(tempCompanyString));

            tempBookIndex = BookSList.indexOf(tempBookString);
            tempWriterIndex = WriterSList.indexOf(tempWriterString);
            tempCompanyIndex = CompanySList.indexOf(tempCompanyString);

            Book haBook = new Book(tempBookString, tempWriterIndex);
            Writer haWriter = new Writer(tempWriterString, tempBookIndex,
                    tempCompanyIndex);
            Company haCompany = new Company(tempCompanyString, tempWriterIndex);
            Row haRow = new Row(haBook, haWriter, haCompany);
            RowList.add(haRow);
            System.out.println(",.,.,.,.,.,.,.,");

            // RowList.add(new Row(new Book(HahaArray[i][0], WriterSList
            // .indexOf(HahaArray[i][1])), new Writer(HahaArray[i][1],
            // BookSList.indexOf(HahaArray[i][0]), CompanySList
            // .indexOf(HahaArray[i][2])), new Company(
            // HahaArray[i][2], WriterSList.indexOf(HahaArray[i][1]))));
            // }
        }

        for (Row rxx : RowList) {

            System.out.println("Book: " + rxx.getBook().getString()
                    + " | getWriterIndex: " + rxx.getBook().getWriterIndex());

            System.out.println("Writer: " + rxx.getWriter().getString()
                    + " | getBookIndex: " + rxx.getWriter().getBookIndex()
                    + " | getCompanyIndex: "
                    + rxx.getWriter().getCompanyIndex());

            System.out.println("Company: " + rxx.getCompany().getString()
                    + " | getBookIndex: " + rxx.getCompany().getWriterIndex());
            System.out.println("============================================");

            tempBookString = rxx.getBook().getString();
            tempWriterString = rxx.getWriter().getString();
            tempCompanyString = rxx.getCompany().getString();

            /*
            TopicIF idTopic = builder.makeTopic();
            builder.makeTopicBook(idTopic, rxx.getBook().getString());
            
            TopicIF writerTopic = builder.makeTopic();
            builder.makeTopicBook(writerTopic, rxx.getWriter().getString());
            
            TopicIF companyTopic = builder.makeTopic();
            builder.makeTopicBook(companyTopic, rxx.getCompany().getString());

            // 创建occurrence
            builder.makeOccurrence(writerTopic, occurenceNW, rxx.getBook().getString());

            // 创建assocation
            AssociationIF associationWC = builder
                    .makeAssociation(topicEmployment);
            builder.makeAssociationRole(associationWC, topicEmployee,
                    writerTopic);
            builder.makeAssociationRole(associationWC, topicEmployer,
                    companyTopic);
            */
        }

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx!");

        /*
        // 删除数组中的重复项1
        int[] nums = { 5, 6, 6, 6, 8, 8, 7, 9, 4, 3, 3, 2, 2, 2, 2, 1 };
        List<Integer> numList = new ArrayList<Integer>();
        for (int i : nums)
            numList.add(i);
        Set<Integer> numSet = new HashSet<Integer>();
        numSet.addAll(numList);
        System.out.println(numSet);

        // 删除数组中的重复项2
        String[] s = { "7", "1", "2", "3", "5", "3", "5", "1", "1", "4" };

        List<String> list = Arrays.asList(s);
        System.out.println(list);
        System.out.println(list.indexOf("1")); // indexOf() 返回第一次出现的位置，1（第二）
        System.out.println(list.lastIndexOf("1")); // lastIndexOf()
                                                   // 返回最后一次出现的位置，8（第九）

        List<String> l = new ArrayList<String>();
        for (String a : list) {
            if (!l.contains(a)) {// contains() 若包含某元素返回true，否则返回false
                l.add(a);// 不包含则加入
            }
        }
        System.out.println(l);
        */

        /*
        // 键盘输入
        try {
        String queryInput = (new BufferedReader(new
        InputStreamReader(System.in))).readLine();
        System.out.println("hah= " + queryInput);
        } catch (IOException e) {
        e.printStackTrace();
        }
        */
    }
}
