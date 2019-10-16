package com.guodian.domin.supplierpage;

/**
 * 物料对账的实体类
 */
public class BomClass {
    /**
     * 单据日期
     */
    private String dPODate;
    /**
     * 数量
     */
    private String iQuantity;
    /**
     * 存货名称
     */
    private String cInvName;
    /**
     * 到期日期
     */
    private String dArriveDate;
    /**
     * 存货编码
     */
    private String cInvCode;
    /**
     * 规格
     */
    private String cInvStd;
    /**
     * 税率
     */
    private String iPerTaxRate;
    /**
     * 无税单价
     */
    private String iUnitPrice;
    /**
     * 无税金额
     */
    private String iMoney;
    /**
     * 累计开票数量
     */
    private String iInvQTY;
    /**
     * 供应商名称
     */
    private String cVenName;
    /**
     * 采购订单号
     */
    private String cPOID;
    /**
     * 含税金额
     */
    private String iSum;
    /**
     * 业务员
     */
    private String cPersonName;
    /**
     * 用户名
     */
    private String Uname;
    /** 含税单价 */
    private String iTaxPrice;
    /** 未到货数量 */
    private String noFPoArrQuantity;
    /***/
    private String noArrQuantity;

    public String getNoArrQuantity() {
        return noArrQuantity;
    }

    public void setNoArrQuantity(String noArrQuantity) {
        this.noArrQuantity = noArrQuantity;
    }

    public String getNoFPoArrQuantity() {
        return noFPoArrQuantity;
    }

    public void setNoFPoArrQuantity(String noFPoArrQuantity) {
        this.noFPoArrQuantity = noFPoArrQuantity;
    }

    public String getiTaxPrice() {
        return iTaxPrice;
    }

    public void setiTaxPrice(String iTaxPrice) {
        this.iTaxPrice = iTaxPrice;
    }

    public String getdPODate() {
        return dPODate;
    }

    public void setdPODate(String dPODate) {
        this.dPODate = dPODate;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
    }

    public String getcInvName() {
        return cInvName;
    }

    public void setcInvName(String cInvName) {
        this.cInvName = cInvName;
    }

    public String getdArriveDate() {
        return dArriveDate;
    }

    public void setdArriveDate(String dArriveDate) {
        this.dArriveDate = dArriveDate;
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

    public String getiPerTaxRate() {
        return iPerTaxRate;
    }

    public void setiPerTaxRate(String iPerTaxRate) {
        this.iPerTaxRate = iPerTaxRate;
    }

    public String getiUnitPrice() {
        return iUnitPrice;
    }

    public void setiUnitPrice(String iUnitPrice) {
        this.iUnitPrice = iUnitPrice;
    }

    public String getiMoney() {
        return iMoney;
    }

    public void setiMoney(String iMoney) {
        this.iMoney = iMoney;
    }

    public String getiInvQTY() {
        return iInvQTY;
    }

    public void setiInvQTY(String iInvQTY) {
        this.iInvQTY = iInvQTY;
    }

    public String getcVenName() {
        return cVenName;
    }

    public void setcVenName(String cVenName) {
        this.cVenName = cVenName;
    }

    public String getcPOID() {
        return cPOID;
    }

    public void setcPOID(String cPOID) {
        this.cPOID = cPOID;
    }

    public String getiSum() {
        return iSum;
    }

    public void setiSum(String iSum) {
        this.iSum = iSum;
    }

    public String getcPersonName() {
        return cPersonName;
    }

    public void setcPersonName(String cPersonName) {
        this.cPersonName = cPersonName;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    @Override
    public String toString() {
        return "BomClass{" +
                "dPODate='" + dPODate + '\'' +
                ", iQuantity='" + iQuantity + '\'' +
                ", cInvName='" + cInvName + '\'' +
                ", dArriveDate='" + dArriveDate + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                ", cInvStd='" + cInvStd + '\'' +
                ", iPerTaxRate='" + iPerTaxRate + '\'' +
                ", iUnitPrice='" + iUnitPrice + '\'' +
                ", iMoney='" + iMoney + '\'' +
                ", iInvQTY='" + iInvQTY + '\'' +
                ", cVenName='" + cVenName + '\'' +
                ", cPOID='" + cPOID + '\'' +
                ", iSum='" + iSum + '\'' +
                ", cPersonName='" + cPersonName + '\'' +
                ", Uname='" + Uname + '\'' +
                ", iTaxPrice='" + iTaxPrice + '\'' +
                ", noFPoArrQuantity='" + noFPoArrQuantity + '\'' +
                ", noArrQuantity='" + noArrQuantity + '\'' +
                '}';
    }
}
