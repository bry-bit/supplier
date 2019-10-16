package com.guodian.domin;

/**
 * 供应商报价单U8关联数据
 */
public class LinkedData {
    private String cCodePlan;
    private String cInvCode;
    private String cVenCode;
    private String cFlag;
    private String VerifyDate;

    public String getVerifyDate() {
        return VerifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        VerifyDate = verifyDate;
    }

    public String getcCodePlan() {
        return cCodePlan;
    }

    public void setcCodePlan(String cCodePlan) {
        this.cCodePlan = cCodePlan;
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

    public String getcFlag() {
        return cFlag;
    }

    public void setcFlag(String cFlag) {
        this.cFlag = cFlag;
    }

    @Override
    public String toString() {
        return "LinkedData{" +
                "cCodePlan='" + cCodePlan + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                ", cVenCode='" + cVenCode + '\'' +
                ", cFlag='" + cFlag + '\'' +
                ", VerifyDate='" + VerifyDate + '\'' +
                '}';
    }
}
