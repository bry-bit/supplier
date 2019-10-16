package com.guodian.domin.system;

import java.io.Serializable;

public class UpdatePass implements Serializable {
    private static final long serialVersionUID = 8845685983981092734L;
    private String oldPass;
    private String newPass;
    private String newPass2;
    private String Uname;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPass2() {
        return newPass2;
    }

    public void setNewPass2(String newPass2) {
        this.newPass2 = newPass2;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "UpdatePass [oldPass=" + oldPass + ", newPass=" + newPass + ", newPass2=" + newPass2 + ", Uname=" + Uname
                + "]";
    }
}
