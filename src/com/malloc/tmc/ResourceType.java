package com.malloc.tmc;

public class ResourceType {

    // 名称，制作人，日期，大小，后缀
    private String resourceName;
    private String resourceCreator;
    private String resourceDate;
    private String resourceSize;
    private String resourceSuiffx;
    private String resourceFullName;

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
        // fullNameBreakUp(sFullName);
        String[] ss = new String[10];
        ss = sFullName.split("-", 5);
        setName(ss[0]);
        setCreator(ss[1]);
        setDate(ss[2]);
        setSize(ss[3]);
        setSuffix(ss[4]);

    }

    // void fullNameBreakUp(String sfn) {
    // String[] ss = new String[10];
    // ss = sfn.split("-", 5);
    // setName(ss[0]);
    // setCreator(ss[1]);
    // setDate(ss[2]);
    // setSize(ss[3]);
    // setSuffix(ss[4]);
    //
    // }


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
    public void printRType() {
        System.out.println("RT : " + this.getFullName()
                + "\nname\t\tcreator\t\tdate\t\tsize\t\tsuffix\n"
                + this.getName() + "\t\t" + this.getCreator() + "\t\t"
                + this.getDate() + "\t\t" + this.getSize() + "\t\t"
                + this.getSuffix() + "\n");
    }

    public void printRType(String sFullName) {

        String[] ss = new String[10];
        ss = sFullName.split("-", 5);

        System.out.println("RT : " + sFullName
                + "\nname\t\tcreator\t\tdate\t\tsize\t\tsuffix\n" + ss[0]
                + "\t\t" + ss[1] + "\t\t" + ss[2] + "\t\t" + ss[3] + "\t\t"
                + ss[4] + "\n");
    }

    // get&set resourceName
    public String getName() {
        return resourceName;
    }

    private void setName(String rcName) {
        resourceName = rcName;
    }

    // get&set resourceCreator
    public String getCreator() {
        return resourceCreator;
    }

    private void setCreator(String rcCreator) {
        resourceCreator = rcCreator;
    }

    // get&set resourceDate
    public String getDate() {
        return resourceDate;
    }

    private void setDate(String rcDate) {
        resourceDate = rcDate;
    }

    // get&set resourceSize
    public String getSize() {
        return resourceSize;
    }

    private void setSize(String rcSize) {
        resourceSize = rcSize;
    }

    // get&set resourceSuiffx
    public String getSuffix() {
        return resourceSuiffx;
    }

    private void setSuffix(String rcSuffix) {
        resourceSuiffx = rcSuffix;
    }

    // get&set resourceFullName
    public String getFullName() {
        return resourceFullName;
    }

    private void setFullName(String rcFullName) {
        resourceFullName = rcFullName;
    }

}
