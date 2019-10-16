package com.guodian.domin.supplierpage;

import java.sql.Date;

/**
 * 审核时的实体类
 */
public class ShenHeClass {
    /** 需求编号 */
    private String ReqID;
    /** 存货编码 */
    private String cInvCode;
    /** 存货名称 */
    private String cInvName;
    /** 最低需求数量 */
    private Float iQuantity;
    /** 发布日期 */
    private Date pubDate;
    /** 到货日期 */
    private Date getDate;
    /** 过期日 */
    private Date outDate;
    /** 供应商名称 */
    private String cVenName;
    /** 供应商编号 */
    private String cVenCode;
    /** 物料单价 */
    private Float iInvSCost;
    /** 模具报价 */
    private Float iModSCost;
    /** 总报价 */
    private Float iSUMCost;
    /** 审核状态 */
    private String memo;
    /** 规格 */
    private String cInvStd;
    /** 计量单位 */
    private String cComUnitName;
    /** 制单人 */
    private String cPsn_Name;
    /** 单据编号 */
    private String cCode;
    /** 是否包含模具 */
    private String mould;
    /** 审核状态 */
    private String status;
    /**退回原因  */
    private String history;
    /** 创建时间 */
    private String creatTime;
    /** 历史状态 */
    private Integer historyStatus;
    /** 图片URL */
    private String imgURL;

    public String getReqID() {
        return ReqID;
    }

    public void setReqID(String reqID) {
        ReqID = reqID;
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

    public Float getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(Float iQuantity) {
        this.iQuantity = iQuantity;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Date getGetDate() {
        return getDate;
    }

    public void setGetDate(Date getDate) {
        this.getDate = getDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getcVenName() {
        return cVenName;
    }

    public void setcVenName(String cVenName) {
        this.cVenName = cVenName;
    }

    public String getcVenCode() {
        return cVenCode;
    }

    public void setcVenCode(String cVenCode) {
        this.cVenCode = cVenCode;
    }

    public Float getiInvSCost() {
        return iInvSCost;
    }

    public void setiInvSCost(Float iInvSCost) {
        this.iInvSCost = iInvSCost;
    }

    public Float getiModSCost() {
        return iModSCost;
    }

    public void setiModSCost(Float iModSCost) {
        this.iModSCost = iModSCost;
    }

    public Float getiSUMCost() {
        return iSUMCost;
    }

    public void setiSUMCost(Float iSUMCost) {
        this.iSUMCost = iSUMCost;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public void setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public void setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Integer historyStatus) {
        this.historyStatus = historyStatus;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return "ShenHeClass{" +
                "ReqID='" + ReqID + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                ", cInvName='" + cInvName + '\'' +
                ", iQuantity='" + iQuantity + '\'' +
                ", pubDate=" + pubDate +
                ", getDate=" + getDate +
                ", outDate=" + outDate +
                ", cVenName='" + cVenName + '\'' +
                ", cVenCode='" + cVenCode + '\'' +
                ", iInvSCost=" + iInvSCost +
                ", iModSCost=" + iModSCost +
                ", iSUMCost=" + iSUMCost +
                ", memo='" + memo + '\'' +
                ", cInvStd='" + cInvStd + '\'' +
                ", cComUnitName='" + cComUnitName + '\'' +
                ", cPsn_Name='" + cPsn_Name + '\'' +
                ", cCode='" + cCode + '\'' +
                ", mould='" + mould + '\'' +
                ", status='" + status + '\'' +
                ", history='" + history + '\'' +
                ", creatTime=" + creatTime +
                ", historyStatus=" + historyStatus +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}
