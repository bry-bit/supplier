package com.guodian.domin.supplierpage;

import java.sql.Date;

/**
 * 挂账查询的实体类
 */
public class PaymentClass {
    /** 已开票金额*/
    private String iOriSum;
    /** 存货名称 */
    private String cInvName;
    /** 发票编号 */
    private String cPBVCode;
    /** 存货编码 */
    private String cInvCode;
    /** 规格 */
    private String cInvStd;
    /** 数量 */
    private String iPBVQuantity;
    /** 无税单价 */
    private String iOriCost;
    /** 含税单价 */
    private String iOriTaxCost;
    /** 税率 */
    private String iTaxRate;
    /** 税额 */
    private String iOriTaxPrice;
    /** 发票日期 */
    private String dVouDate;
    /** 结算日期 */
    private String dSDate;
    /** 审核日期 */
    private String dverifydate;
    /** 业务人员 */
    private String cPersonName;
    /** 供应商名称 */
    private String cVenName;
    /** 无税金额 */
    private String iOriMoney;
    /** 采购订单号 */
    private String cPOID;
    /** 入库单号 */
    private String cInCode;

    public String getcPOID() {
        return cPOID;
    }

    public void setcPOID(String cPOID) {
        this.cPOID = cPOID;
    }

    public String getcInCode() {
        return cInCode;
    }

    public void setcInCode(String cInCode) {
        this.cInCode = cInCode;
    }

    public String getiOriMoney() {
        return iOriMoney;
    }

    public void setiOriMoney(String iOriMoney) {
        this.iOriMoney = iOriMoney;
    }

    public String getcVenName() {
        return cVenName;
    }

    public void setcVenName(String cVenName) {
        this.cVenName = cVenName;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public void setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
    }

    public String getiOriSum() {
        return iOriSum;
    }

    public void setiOriSum(String iOriSum) {
        this.iOriSum = iOriSum;
    }

    public String getcInvName() {
        return cInvName;
    }

    public void setcInvName(String cInvName) {
        this.cInvName = cInvName;
    }

    public String getiPBVQuantity() {
        return iPBVQuantity;
    }

    public void setiPBVQuantity(String iPBVQuantity) {
        this.iPBVQuantity = iPBVQuantity;
    }

    public String getiOriCost() {
        return iOriCost;
    }

    public void setiOriCost(String iOriCost) {
        this.iOriCost = iOriCost;
    }

    public String getiOriTaxCost() {
        return iOriTaxCost;
    }

    public void setiOriTaxCost(String iOriTaxCost) {
        this.iOriTaxCost = iOriTaxCost;
    }

    public String getiTaxRate() {
        return iTaxRate;
    }

    public void setiTaxRate(String iTaxRate) {
        this.iTaxRate = iTaxRate;
    }

    public String getiOriTaxPrice() {
        return iOriTaxPrice;
    }

    public void setiOriTaxPrice(String iOriTaxPrice) {
        this.iOriTaxPrice = iOriTaxPrice;
    }

    public String getdVouDate() {
        return dVouDate;
    }

    public void setdVouDate(String dVouDate) {
        this.dVouDate = dVouDate;
    }

    public String getdSDate() {
        return dSDate;
    }

    public void setdSDate(String dSDate) {
        this.dSDate = dSDate;
    }

    public String getDverifydate() {
        return dverifydate;
    }

    public void setDverifydate(String dverifydate) {
        this.dverifydate = dverifydate;
    }

    public String getcPersonName() {
        return cPersonName;
    }

    public void setcPersonName(String cPersonName) {
        this.cPersonName = cPersonName;
    }

    public String getcPBVCode() {
        return cPBVCode;
    }

    public void setcPBVCode(String cPBVCode) {
        this.cPBVCode = cPBVCode;
    }

    @Override
    public String toString() {
        return "PaymentClass{" +
                "iOriSum='" + iOriSum + '\'' +
                ", cInvName='" + cInvName + '\'' +
                ", cPBVCode='" + cPBVCode + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                ", cInvStd='" + cInvStd + '\'' +
                ", iPBVQuantity='" + iPBVQuantity + '\'' +
                ", iOriCost='" + iOriCost + '\'' +
                ", iOriTaxCost='" + iOriTaxCost + '\'' +
                ", iTaxRate='" + iTaxRate + '\'' +
                ", iOriTaxPrice='" + iOriTaxPrice + '\'' +
                ", dVouDate='" + dVouDate + '\'' +
                ", dSDate='" + dSDate + '\'' +
                ", dverifydate='" + dverifydate + '\'' +
                ", cPersonName='" + cPersonName + '\'' +
                ", cVenName='" + cVenName + '\'' +
                ", iOriMoney='" + iOriMoney + '\'' +
                ", cPOID='" + cPOID + '\'' +
                ", cInCode='" + cInCode + '\'' +
                '}';
    }
}
