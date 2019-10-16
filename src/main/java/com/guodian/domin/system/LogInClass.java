package com.guodian.domin.system;

/**
 * 登录注册的实体类
 */
public class LogInClass {
    /** 供应商名称 */
    private String cVenName;
    /** 用户名 */
    private String Uname;
    /** 电话 */
    private String Cphone;
    /** 邮件 */
    private String Email;
    /** 备注 */
    private String Memo;
    /** 权限 */
    private int Admin;
    /** 密码 */
    private String Pass;

    public String getcVenName() {
        return cVenName;
    }

    public void setcVenName(String cVenName) {
        this.cVenName = cVenName;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getCphone() {
        return Cphone;
    }

    public void setCphone(String cphone) {
        Cphone = cphone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public int getAdmin() {
        return Admin;
    }

    public void setAdmin(int admin) {
        Admin = admin;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "cVenName='" + cVenName + '\'' +
                ", Uname='" + Uname + '\'' +
                ", Cphone='" + Cphone + '\'' +
                ", Email='" + Email + '\'' +
                ", Memo='" + Memo + '\'' +
                ", Admin=" + Admin +
                ", Pass='" + Pass + '\'' +
                '}';
    }
}
