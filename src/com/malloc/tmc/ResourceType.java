package com.malloc.tmc;

public class ResourceType {

    // 名称，制作人，日期，大小，后缀
    String resourceName;
    String resourceCreator;
    String resourceDate;
    String resourceSize;
    String resourceSuiffx;
    String resourceFullName;

    // private String resourceName;
    // private String resourceCreator;
    // private String resourceDate;
    // private String resourceSize;
    // private String resourceSuiffx;
    // private String resourceFullName;

    public ResourceType() {
        resourceName = null;
        resourceCreator = null;
        resourceDate = null;
        resourceSize = null;
        resourceSuiffx = null;
        resourceFullName = null;
    }

    // 将FullName分解
    public ResourceType(String sFullName) {
        setFullName(sFullName);
        fullNameBreakUp();

    }

    //
    public ResourceType(String sName, String sCreator, String sDate,
            String sSize, String sSuiffx) {
        setName(sName);
        setCreator(sCreator);
        setDate(sDate);
        setSize(sSize);
        setSuffix(sSuiffx);
        setFullName(sName + "-" + sCreator + "-" + sDate + "-" + sSize + "-"
                + sSuiffx);
    }

    void fullNameBreakUp() {

    }

    public void printRType() {
        System.out.println("RT : " + this.getFullName()
                + "\nname\tcreator\tdate\tsize\tsuffix\n" + this.getName()
                + "\t" + this.getCreator() + "\t" + this.getDate() + "\t"
                + this.getSize() + "\t" + this.getSuffix() + "\n");
    }

    // get&set resourceName
    public String getName() {
        return resourceName;
    }

    public void setName(String rcName) {
        resourceName = rcName;
    }

    // get&set resourceCreator
    public String getCreator() {
        return resourceCreator;
    }

    public void setCreator(String rcCreator) {
        resourceCreator = rcCreator;
    }

    // get&set resourceDate
    public String getDate() {
        return resourceDate;
    }

    public void setDate(String rcDate) {
        resourceDate = rcDate;
    }

    // get&set resourceSize
    public String getSize() {
        return resourceSize;
    }

    public void setSize(String rcSize) {
        resourceSize = rcSize;
    }

    // get&set resourceSuiffx
    public String getSuffix() {
        return resourceSuiffx;
    }

    public void setSuffix(String rcSuffix) {
        resourceSuiffx = rcSuffix;
    }

    // get&set resourceFullName
    public String getFullName() {
        return resourceFullName;
    }

    public void setFullName(String rcFullName) {
        resourceFullName = rcFullName;
    }

}
