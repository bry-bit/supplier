package com.guodian.domin.supplierpage;

/**
 * 通过时间段查询数据库的实体类
 */
public class SearchDateClass {
    /** 单据编号 */
    private String cCode;
    /** 存货名称 */
    private String cInvName;
    /** 存货编码 */
    private String cInvCode;
    /** 原币无税金额 */
    private String iMoney;
    /** 到货日期 */
    private String dbarvdate;
    /** 单据日期 */
    private String dDate;
    /** 规格 */
    private String cInvStd;
    /** 税率 */
    private String iTaxRate;
    /** 供应商 */
    private String cVenName;
    /** 无税单价 */
    private String iOriCost;
    /** 无税金额 */
    private String iOriMoney;
    /** 累计开票数量 */
    private String iSumBillQuantity;
    /** 业务员 */
    private String cPersonName;
    /** 权限 */
    private int Admin;
    /** 用户名 */
    private String Uname;
    /** 采购订单号 */
    private String cPOID;
    /** 含税单价 */
    private String iOriTaxCost;
    /** 含税金额 */
    private String iOriPrice;
    /** 数量 */
    private String iQuantity;

	public String getiQuantity() {
		return iQuantity;
	}

	public void setiQuantity(String iQuantity) {
		this.iQuantity = iQuantity;
	}

	public String getcPOID() {
		return cPOID;
	}

	public void setcPOID(String cPOID) {
		this.cPOID = cPOID;
	}

	public String getiOriTaxCost() {
		return iOriTaxCost;
	}

	public void setiOriTaxCost(String iOriTaxCost) {
		this.iOriTaxCost = iOriTaxCost;
	}

	public String getiOriPrice() {
		return iOriPrice;
	}

	public void setiOriPrice(String iOriPrice) {
		this.iOriPrice = iOriPrice;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
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
	public String getDbarvdate() {
		return dbarvdate;
	}
	public void setDbarvdate(String dbarvdate) {
		this.dbarvdate = dbarvdate;
	}
	public String getdDate() {
		return dDate;
	}
	public void setdDate(String dDate) {
		this.dDate = dDate;
	}
	public String getcInvStd() {
		return cInvStd;
	}
	public void setcInvStd(String cInvStd) {
		this.cInvStd = cInvStd;
	}
	public String getiTaxRate() {
		return iTaxRate;
	}
	public void setiTaxRate(String iTaxRate) {
		this.iTaxRate = iTaxRate;
	}
	public String getcVenName() {
		return cVenName;
	}
	public void setcVenName(String cVenName) {
		this.cVenName = cVenName;
	}
	public String getiOriCost() {
		return iOriCost;
	}
	public void setiOriCost(String iOriCost) {
		this.iOriCost = iOriCost;
	}
	public String getiOriMoney() {
		return iOriMoney;
	}
	public void setiOriMoney(String iOriMoney) {
		this.iOriMoney = iOriMoney;
	}
	public String getiSumBillQuantity() {
		return iSumBillQuantity;
	}
	public void setiSumBillQuantity(String iSumBillQuantity) {
		this.iSumBillQuantity = iSumBillQuantity;
	}
	public String getcPersonName() {
		return cPersonName;
	}
	public void setcPersonName(String cPersonName) {
		this.cPersonName = cPersonName;
	}
	public int getAdmin() {
		return Admin;
	}
	public void setAdmin(int admin) {
		Admin = admin;
	}
	public String getUname() {
		return Uname;
	}
	public void setUname(String uname) {
		Uname = uname;
	}

	@Override
	public String toString() {
		return "SearchDateClass{" +
				"cCode='" + cCode + '\'' +
				", cInvName='" + cInvName + '\'' +
				", cInvCode='" + cInvCode + '\'' +
				", iMoney='" + iMoney + '\'' +
				", dbarvdate='" + dbarvdate + '\'' +
				", dDate='" + dDate + '\'' +
				", cInvStd='" + cInvStd + '\'' +
				", iTaxRate='" + iTaxRate + '\'' +
				", cVenName='" + cVenName + '\'' +
				", iOriCost='" + iOriCost + '\'' +
				", iOriMoney='" + iOriMoney + '\'' +
				", iSumBillQuantity='" + iSumBillQuantity + '\'' +
				", cPersonName='" + cPersonName + '\'' +
				", Admin=" + Admin +
				", Uname='" + Uname + '\'' +
				", cPOID='" + cPOID + '\'' +
				", iOriTaxCost='" + iOriTaxCost + '\'' +
				", iOriPrice='" + iOriPrice + '\'' +
				", iQuantity='" + iQuantity + '\'' +
				'}';
	}
}
