package com.guodian.domin;

/**
 * 采购比价审批单U8关联数据
 */
public class LinkedDatas {
    private String cFlag;
    private String PlanID;
    private String cInvCode;
    private String cVenCode;
    private String VerifyDate;

    public String getVerifyDate() {
        return VerifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        VerifyDate = verifyDate;
    }

    public String getcFlag() {
        return cFlag;
    }

    public void setcFlag(String cFlag) {
        this.cFlag = cFlag;
    }

    public String getPlanID() {
        return PlanID;
    }

    public void setPlanID(String planID) {
        PlanID = planID;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getcVenCode() {
        return cVenCode;
    }

    public void setcVenCode(String cVenCode) {
        this.cVenCode = cVenCode;
    }

    @Override
    public String toString() {
        return "LinkedDatas{" +
                "cFlag='" + cFlag + '\'' +
                ", PlanID='" + PlanID + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                ", cVenCode='" + cVenCode + '\'' +
                ", VerifyDate='" + VerifyDate + '\'' +
                '}';
    }
}
