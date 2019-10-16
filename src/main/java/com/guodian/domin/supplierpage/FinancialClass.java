package com.guodian.domin.supplierpage;

import java.sql.Date;

/**
 * 财务对账的实体类
 */
public class FinancialClass {
    /** 单据编号 */
    private String id;
    /** 存货名称 */
    private String cInvName;
    /** 存货编码 */
    private String cInvCode;
    /** 原币无税金额 */
    private String iMoney;
    /** 到货日期 */
    private Date dbarvdate;
    /** 单据日期 */
    private Date dDate;
    /** 规格 */
    private String cInvStd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcInvName() {
        return cInvName;
    }

    public void setcInvName(String cInvName) {
        this.cInvName = cInvName;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getiMoney() {
        return iMoney;
    }

    public void setiMoney(String iMoney) {
        this.iMoney = iMoney;
    }

    public Date getDbarvdate() {
        return dbarvdate;
    }

    public void setDbarvdate(Date dbarvdate) {
        this.dbarvdate = dbarvdate;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public void setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
    }

    @Override
    public String toString() {
        return "FinancialClass{" +
                "id='" + id + '\'' +
                ", cInvName='" + cInvName + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                ", iMoney='" + iMoney + '\'' +
                ", dbarvdate=" + dbarvdate +
                ", dDate=" + dDate +
                ", cInvStd='" + cInvStd + '\'' +
                '}';
    }
}
