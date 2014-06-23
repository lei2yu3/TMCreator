package com.test.tmc;

import net.ontopia.topicmaps.core.TopicIF;

public class Resource {

    // resource attribute
    private String resourceName;
    private String resourceCreator;
    private String resourceDate;
    private String resourceSize;
    private String resourceSuiffx;
    private String resourceFileName;
    public TopicIF resourceTopic;

    public Resource() {
        resourceName = null;
        resourceCreator = null;
        resourceDate = null;
        resourceSize = null;
        resourceSuiffx = null;
        resourceFileName = null;
        resourceTopic = null;
    }

    // 分解FileName，初始化各个属性
    public Resource(String sFileName) {
        String[] ss = new String[5];
        ss = sFileName.split("-", 5);

        setResourceName(ss[0]);
        setResourceCreator(ss[1]);
        setResourceDate(ss[2]);
        setResourceSize(ss[3]);
        setResourceSuffix(ss[4]);

        setResourceFileName(sFileName);
        resourceTopic = null;
    }

    //
    public Resource(String sName, String sCreator, String sDate, String sSize,
            String sSuiffx) {
        setResourceName(sName);
        setResourceCreator(sCreator);
        setResourceDate(sDate);
        setResourceSize(sSize);
        setResourceSuffix(sSuiffx);
        setResourceFileName(sName + "-" + sCreator + "-" + sDate + "-" + sSize
                + "-" + sSuiffx);
        resourceTopic = null;
    }

    public void resourcePrint() {
        System.out.println("RT : " + this.getResourceFileName()
                + "\nname\t\tcreator\t\tdate\t\tsize\t\tsuffix\n"
                + this.getResourceName() + "\t\t" + this.getResourceCreator()
                + "\t\t" + this.getResourceDate() + "\t"
                + this.getResourceSize() + "\t\t" + this.getResourceSuffix()
                + "\n");
    }

    public void resourcePrint(Resource rt) {
        System.out.println("RT : " + rt.getResourceFileName()
                + "\nname\t\tcreator\t\tdate\t\tsize\t\tsuffix\n"
                + rt.getResourceName() + "\t\t" + rt.getResourceCreator()
                + "\t\t" + rt.getResourceDate() + "\t" + rt.getResourceSize()
                + "\t\t" + rt.getResourceSuffix() + "\n");
    }

    public void resourcePrint(String sFileName) {

        String[] ss = new String[10];
        ss = sFileName.split("-", 5);

        System.out.println("RT : " + sFileName
                + "\nname\t\tcreator\t\tdate\t\tsize\t\tsuffix\n" + ss[0]
                + "\t\t" + ss[1] + "\t\t" + ss[2] + "\t" + ss[3] + "\t\t"
                + ss[4] + "\n");
    }

    // get&set resourceName
    public String getResourceName() {
        return resourceName;
    }

    private void setResourceName(String rcName) {
        resourceName = rcName;
    }

    // get&set resourceCreator
    public String getResourceCreator() {
        return resourceCreator;
    }

    private void setResourceCreator(String rcCreator) {
        resourceCreator = rcCreator;
    }

    // get&set resourceDate
    public String getResourceDate() {
        return resourceDate;
    }

    private void setResourceDate(String rcDate) {
        resourceDate = rcDate;
    }

    // get&set resourceSize
    public String getResourceSize() {
        return resourceSize;
    }

    private void setResourceSize(String rcSize) {
        resourceSize = rcSize;
    }

    // get&set resourceSuiffx
    public String getResourceSuffix() {
        return resourceSuiffx;
    }

    private void setResourceSuffix(String rcSuffix) {
        resourceSuiffx = rcSuffix;
    }

    // get&set resourceFileName
    public String getResourceFileName() {
        return resourceFileName;
    }

    private void setResourceFileName(String rcFileName) {
        resourceFileName = rcFileName;
    }

    // get&set resourceTopic
    public TopicIF getResourceTopic() {
        return this.resourceTopic;
    }
}