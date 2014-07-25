package com.malloc.rdbms;

import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;

/**
 * 
 * 读取二维数组
 * 
 */

public class BinaryArrayReader {
    static String[][] BArray = { { "0", "eee", "HHH" }, { "1", "aaa", "UUU" },
            { "2", "sss", "JJJ" }, { "3", "ddd", "MMM" },
            { "4", "sss", "NNN" }, { "5", "xxx", "KKK" },
            { "6", "www", "HHH" }, { "7", "qqq", "HHH" },
            { "8", "ddd", "NNN" }, { "9", "eee", "YYY" },
            { "10", "aaa", "MMM" }, { "11", "ddd", "YYY" },
            { "12", "www", "NNN" }, { "13", "www", "MMM" },
            { "14", "eee", "MMM" }, { "15", "sss", "MMM" } };

    public static void main(String[] args) {

        ArrayList<Item> alist = new ArrayList<Item>();
        ArrayList<String> id = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> writer = new ArrayList<String>();

        String a = null;
        String b = null;

        int i, j, k;
        for (i = 0; i < BArray.length; i++) {
            Item res = new Item(BArray[i][0], BArray[i][1], BArray[i][2]);
            // Item a = new Item("a", "b", "c");
            res.resourcePrint();
            alist.add(res);
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        for (i = 0; i < alist.size(); i++) {

            // name
            for (j = 0; j < i; j++) {
                if (alist.get(i).getName() == alist.get(j).getName()) {

                    break;
                }
            }

            if (i == j) {
                name.add(alist.get(i).getName());
            }

            // writer
            for (k = 0; k < i; k++) {
                if (alist.get(i).getWriter() == alist.get(k).getWriter()) {
                    break;
                }
            }

            if (i == k) {
                writer.add(alist.get(i).getWriter());
            }

            // print

            a = alist.get(j).getName();
            b = alist.get(k).getWriter();

            System.out.println(" (a, b) = (" + a + ", " + b + ")");
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        for (String n : name) {
            System.out.println("name = " + n.toString());
        }
        System.out.println("name.size = " + name.size());

        for (String w : writer) {
            System.out.println("writer = " + w.toString());
        }
        System.out.println("writer.size = " + writer.size());

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }
}

//
class Item {

    // resource attribute
    private String ID;
    private String Name;
    private String Writer;

    public Item() {
        ID = null;
        Name = null;
        Writer = null;
    }

    //
    public Item(String sID, String sName, String sWriter) {
        setID(sID);
        setName(sName);
        setWriter(sWriter);
    }

    public void resourcePrint() {
        System.out.println("(" + this.getID() + "==" + this.getName() + "=="
                + this.getWriter() + ")");
    }

    public void resourcePrint(Resource rt) {
        System.out.println("(" + rt.getID() + "==" + rt.getName() + "=="
                + rt.getWriter() + ")");
    }

    // get&set ID
    public String getID() {
        return ID;
    }

    private void setID(String rID) {
        ID = rID;
    }

    // get&set Name
    public String getName() {
        return Name;
    }

    private void setName(String rName) {
        Name = rName;
    }

    // get&set Writer
    public String getWriter() {
        return Writer;
    }

    private void setWriter(String rWriter) {
        Writer = rWriter;
    }

}
// ===============================================================
/*
 * ArrayList<TopicIF> nameList = new ArrayList<TopicIF>(); ArrayList<TopicIF>
 * writerList = new ArrayList<TopicIF>(); ArrayList<TopicIF> companyList = new
 * ArrayList<TopicIF>();
 * 
 * // topic name string String nameString;// = null; String writerString;// =
 * null; String companyString;// = null;
 * 
 * // temp topic TopicIF nameTopic; TopicIF writerTopic = null; TopicIF
 * companyTopic = null;
 * 
 * // 添加Writer-Comany关联 TopicIF topicEmployment = builder.makeTopic();
 * builder.makeTopicName(topicEmployment, "Employment");
 * 
 * TopicIF topicEmployee = builder.makeTopic();
 * builder.makeTopicName(topicEmployee, "Employee");
 * 
 * TopicIF topicEmployer = builder.makeTopic();
 * builder.makeTopicName(topicEmployer, "Employer");
 * 
 * // 添加Name-Writer资源实例 TopicIF occurenceNW = builder.makeTopic();
 * builder.makeTopicName(occurenceNW, "0cc-NW");
 * 
 * // ArrayList<Resource> resourceList = new ArrayList<Resource>();
 * 
 * 
 * int i, j, k; for (i = 0; i < resourceList.size(); i++) { nameString =
 * resourceList.get(i).getName(); writerString =
 * resourceList.get(i).getWriter(); companyString =
 * resourceList.get(i).getCompany();
 * 
 * for (j = 0; j < i; j++) { // if
 * (writerString.equals(resourceList.get(j).getWriter())) // { // writerTopic =
 * writerList.get(writerList.size() - 1); if
 * (writerString.equals(writerList.get(j).toString())) { writerTopic =
 * writerList.get(j); break; } }
 * 
 * if (i == j) { writerTopic = builder.makeTopic();
 * builder.makeTopicName(writerTopic, resourceList.get(j) .getWriter());
 * 
 * writerList.add(writerTopic); }
 * 
 * // company
 * 
 * for (k = 0; k < i; k++) { // if //
 * (companyString.equals(resourceList.get(k).getCompany())) // { // companyTopic
 * = companyList.get(companyList.size() - 1); if
 * (companyString.equals(companyList.get(k).toString())) { companyTopic =
 * companyList.get(k); break; } }
 * 
 * if (i == k) { companyTopic = builder.makeTopic();
 * builder.makeTopicName(companyTopic, resourceList.get(k) .getCompany());
 * 
 * companyList.add(companyTopic); }
 * 
 * // 创建occurrence builder.makeOccurrence(writerTopic, occurenceNW, nameString);
 * 
 * // 创建assocation AssociationIF associationWC = builder
 * .makeAssociation(topicEmployment); builder.makeAssociationRole(associationWC,
 * topicEmployee, writerTopic); builder.makeAssociationRole(associationWC,
 * topicEmployer, companyTopic);
 */