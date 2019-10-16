package com.guodian.domin.bidding;

/**
 * 查询供应商档案的实体类
 */
public class SupplierFileClass {
    /** 供应商编码 */
    private String cVenCode;
    /** 供应商名称 */
    private String cVenName;

    public String getcVenCode() {
        return cVenCode;
    }

    public void setcVenCode(String cVenCode) {
        this.cVenCode = cVenCode;
    }

    public String getcVenName() {
        return cVenName;
    }

    public void setcVenName(String cVenName) {
        this.cVenName = cVenName;
    }

    @Override
    public String toString() {
        return "SupplierFileClass{" +
                "cVenCode='" + cVenCode + '\'' +
                ", cVenName='" + cVenName + '\'' +
                '}';
    }
}
