package com.guodian.domin.system;

import java.io.Serializable;

public class Login implements Serializable {
    private static final long serialVersionUID = 4273976463165749821L;
    private Integer Admin;
    private String Uname;
    private String Pass;
    public Login() {
		super();
	}
	public Login(Integer admin) {
		super();
		Admin = admin;
	}

	public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getAdmin() {
        return Admin;
    }

    public void setAdmin(Integer admin) {
        Admin = admin;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    @Override
    public String toString() {
        return "Login{" +
                "Admin=" + Admin +
                ", Uname='" + Uname + '\'' +
                ", Pass='" + Pass + '\'' +
                '}';
    }
}
