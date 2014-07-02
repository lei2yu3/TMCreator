package com.malloc.rdbms;

public class Resource {

    // resource attribute
    private int ID;
    private String Name;
    private String Writer;
    private String Company;
    private String Path;

    public Resource() {
        ID = 0;
        Name = null;
        Writer = null;
        Company = null;
        Path = null;
    }

    //
    public Resource(int fID, String sName, String sWriter, String sCompany,
            String sPath) {
        setID(fID);
        setName(sName);
        setWriter(sWriter);
        setCompany(sCompany);
        setPath(sPath);
    }

    public void resourcePrint() {
        System.out.println("(" + this.getID() + "==" + this.getName() + "=="
                + this.getWriter() + "==" + this.getCompany() + "=="
                + this.getPath() + ")");
    }

    public void resourcePrint(Resource rt) {
        System.out.println("(" + rt.getID() + "==" + rt.getName() + "=="
                + rt.getWriter() + "==" + rt.getCompany() + "=="
                + this.getPath() + ")");
    }

    // get&set ID
    public int getID() {
        return ID;
    }

    private void setID(int rID) {
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

    // get&set Company
    public String getCompany() {
        return Company;
    }

    private void setCompany(String rCompany) {
        Company = rCompany;
    }

    // get&set Path
    public String getPath() {
        return Path;
    }

    private void setPath(String rPath) {
        Path = rPath;
    }
}
