package com.guodian.domin.bidding;

/**
 * 发布新需求时所需的实体类
 */
public class SubmitReleaseClass {
    /** 存货id*/
    private String reqID;
    /** 存货code*/
    private String cInvCode;
    /** 存货名称 */
    private String cInvName;
    /** 最低需求数量 */
    private String iQuantity;
    /** 调价单最新价格 */
    private String iInvRCost;
    /** 备选供应商列表 */
    private String pVenName;
    /** 图片URL */
    private String imgURL;
    /** 原图URL */
    private String souURL;
    /** 发布日期 */
    private String pubDate;
    /** 到货日期 */
    private String getDate;
    /** 过期日 */
    private String outDate;
    /** 备注 */
    private String memo;
    /** 计量单位 */
    private String cComUnitName;
    /** 规格 */
    private String cInvStd;
    /** 制单人 */
    private String cPsn_Name;
    /** 单据编号 */
    private String cCode;
    /** 是否包含模具 */
    private String mould;
    /** 存储的状态 */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
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

    public String getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
    }

    public String getiInvRCost() {
        return iInvRCost;
    }

    public void setiInvRCost(String iInvRCost) {
        this.iInvRCost = iInvRCost;
    }

    public String getpVenName() {
        return pVenName;
    }

    public void setpVenName(String pVenName) {
        this.pVenName = pVenName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getSouURL() {
        return souURL;
    }

    public void setSouURL(String souURL) {
        this.souURL = souURL;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGetDate() {
        return getDate;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public void setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public void setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
    }

    public String getcPsn_Name() {
        return cPsn_Name;
    }

    public void setcPsn_Name(String cPsn_Name) {
        this.cPsn_Name = cPsn_Name;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getMould() {
        return mould;
    }

    public void setMould(String mould) {
        this.mould = mould;
    }


    @Override
    public String toString() {
        return "SubmitReleaseClass{" +
                "reqID='" + reqID + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                ", cInvName='" + cInvName + '\'' +
                ", iQuantity='" + iQuantity + '\'' +
                ", iInvRCost='" + iInvRCost + '\'' +
                ", pVenName='" + pVenName + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", souURL='" + souURL + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", getDate='" + getDate + '\'' +
                ", outDate='" + outDate + '\'' +
                ", memo='" + memo + '\'' +
                ", cComUnitName='" + cComUnitName + '\'' +
                ", cInvStd='" + cInvStd + '\'' +
                ", cPsn_Name='" + cPsn_Name + '\'' +
                ", cCode='" + cCode + '\'' +
                ", mould='" + mould + '\'' +
                ", status=" + status +
                '}';
    }
}
