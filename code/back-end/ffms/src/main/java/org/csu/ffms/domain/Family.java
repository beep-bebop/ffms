package org.csu.ffms.domain;

public class Family {
    private String familyid;
    private String familyname;
    private String familykey;

    public void setFamilykey(String familykey) {
        this.familykey = familykey;
    }

    public String getFamilykey() {
        return familykey;
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }
}
