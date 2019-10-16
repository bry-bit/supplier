package com.guodian.domin.bidding;

/**
 * 获取存货档案数据和计量单位数据
 * 的实体类
 */
public class NewDemandClass {
    /** 存货编号 */
    private String cInvCode;
    /** 存货名称 */
    private String cInvName;
    /** 计量单位 */
    private String cComUnitName;
    /** 规格 */
    private String cInvStd;

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

    @Override
    public String toString() {
        return "Inventory{" +
                "cInvCode='" + cInvCode + '\'' +
                ", cInvName='" + cInvName + '\'' +
                ", cComUnitName='" + cComUnitName + '\'' +
                ", cInvStd='" + cInvStd + '\'' +
                '}';
    }
}
