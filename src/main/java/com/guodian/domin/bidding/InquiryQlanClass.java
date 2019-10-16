package com.guodian.domin.bidding;

import java.sql.Date;

/**
 * 询价计划单的实体类
 */
public class InquiryQlanClass {
    /** 存货编码 */
    private String cInvCode;
    /** 存货名称 */
    private String cInvName;
    /** 计划日期 */
    private String cDate;
    /** 计划采购数量 */
    private Float qty;
    /** 需求日期 */
    private String ddate;
    /** 规格 */
    private String cInvstd;
    /** 计量单位 */
    private String cInvit;
    /** 单据编号 */
    private String cCode;
    /** 制单人 */
    private String cPsn_Name;
    /** 进行条件限制 */
    private String cdefine22;

    public String getCdefine22() {
        return cdefine22;
    }

    public void setCdefine22(String cdefine22) {
        this.cdefine22 = cdefine22;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcPsn_Name() {
        return cPsn_Name;
    }

    public void setcPsn_Name(String cPsn_Name) {
        this.cPsn_Name = cPsn_Name;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getcInvName() {
        return cInvName;
    }

    public void setcInvName(String cInvName) {
        this.cInvName = cInvName;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }


    public String getcInvstd() {
        return cInvstd;
    }

    public void setcInvstd(String cInvstd) {
        this.cInvstd = cInvstd;
    }

    public String getcInvit() {
        return cInvit;
    }

    public void setcInvit(String cInvit) {
        this.cInvit = cInvit;
    }

    @Override
    public String toString() {
        return "InquiryQlanClass{" +
                "cInvCode='" + cInvCode + '\'' +
                ", cInvName='" + cInvName + '\'' +
                ", cDate=" + cDate +
                ", qty=" + qty +
                ", ddate=" + ddate +
                ", cInvstd='" + cInvstd + '\'' +
                ", cInvit='" + cInvit + '\'' +
                ", cCode='" + cCode + '\'' +
                ", cPsn_Name='" + cPsn_Name + '\'' +
                ", cdefine22='" + cdefine22 + '\'' +
                '}';
    }
}
