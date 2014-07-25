package com.test.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// 基本单元
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

class Row {

    private ID rowID;// = new ID();
    private Writer rowWriter;// = new Writer();
    private Company rowCompany;// = new Company();

    //
    public Row(ID i, Writer w, Company c) {
        rowID = new ID(i.getString(), i.getWriterIndex());
        rowWriter = new Writer(w.getString(), w.getIDIndex(), w.getCompanyIndex());
        rowCompany = new Company(c.getString(), c.getWriterIndex());
    }


    public ID getID() {
        return rowID;
    }

//    private void setID(ID ii) {
//        rowID = ii;
//    }
    
    public Writer getWriter() {
        return rowWriter;
    }

//    private void setWriter(Writer ww) {
//        rowWriter = ww;
//    }

    public Company getCompany() {
        return rowCompany;
    }

//    private void setCompany(Company cc) {
//        rowCompany = cc;
//    }
}

public class aaa {

    //
    ArrayList<ID> IDList;
    ArrayList<Writer> WriterList;
    ArrayList<Company> CompanyList;


    public static void main(String[] args) {
        
        ID ri = new ID("idxxx", 11);
        Writer rw = new Writer("writerxxx", 22, 33);
        Company rc = new Company("companyxxx", 44);
        Row rr = new Row(ri, rw, rc);
        System.out.println(rr.getID().getString());
        System.out.println(rr.getID().getWriterIndex());

        System.out.println("======================");

        System.out.println(rr.getWriter().getString());
        System.out.println(rr.getWriter().getIDIndex());
        System.out.println(rr.getWriter().getCompanyIndex());

        System.out.println("======================");

        System.out.println(rr.getCompany().getString());
        System.out.println(rr.getCompany().getWriterIndex());
        
        
        ID rii = new ID();
        Writer rww = new Writer();
        Company rcc = new Company();
        Row rrr = new Row(rii, rww, rcc);
        
        
        // 键盘输入
        // try {
        // String queryInput = (new BufferedReader(new
        // InputStreamReader(System.in))).readLine();
        // System.out.println("hah= " + queryInput);
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        //

    }
}
