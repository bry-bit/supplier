package com.guodian.controller.bidding;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.LinkedData;
import com.guodian.domin.LinkedDatas;
import com.guodian.domin.supplierpage.BaoJiaClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 供应商报价
 */
@RestController
@Transactional
public class QuotationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //String类型的运算
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    /**
     * 将报价表的数据展现在页面上
     *
     * @return
     */
    @RequestMapping("/supplierAudit")
    public String supplierAudit(String username, String Admin) {
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
        if (num.equals("0")) {
            //采购比价单
            String SQL1 = "select HY_DZ_HU_QuoteDetails.cFlag,HY_DZ_HU_QuoteMain.PlanID,HY_DZ_HU_QuoteDetails.cInvCode" +
                    ",HY_DZ_HU_QuoteMain.VerifyDate,HY_DZ_HU_QuoteDetails.cVenCode from HY_DZ_HU_QuoteDetails,HY_DZ_HU_QuoteMain " +
                    "where HY_DZ_HU_QuoteMain.ID = HY_DZ_HU_QuoteDetails.id";
            List<LinkedDatas> linkedDatas = jdbcTemplate.query(SQL1, new Object[]{}, new BeanPropertyRowMapper<>(LinkedDatas.class));
            //供应商报价单
            String SQL2 = "select HY_DZ_HU_ConsultPrices.cCodePlan,HY_DZ_HU_ConsultPrices.cInvCode,HY_DZ_HU_ConsultPrice.cVenCode " +
                    "from HY_DZ_HU_ConsultPrice,HY_DZ_HU_ConsultPrices where HY_DZ_HU_ConsultPrice.ID = HY_DZ_HU_ConsultPrices.ID";
            List<LinkedData> linkedData = jdbcTemplate.query(SQL2, new Object[]{}, new BeanPropertyRowMapper<>(LinkedData.class));
            //循环这两个集合当if条件符合是将cFlag状态存到新的list集合里
            List<LinkedData> list = new ArrayList<>();
            for (LinkedDatas datas : linkedDatas) {
                for (LinkedData data : linkedData) {
                    if (datas.getcInvCode().equals(data.getcInvCode()) &&
                            datas.getPlanID().equals(data.getcCodePlan()) &&
                            datas.getcVenCode().equals(data.getcVenCode())) {
                        data.setcFlag(datas.getcFlag());
                        data.setVerifyDate(datas.getVerifyDate());
                        list.add(data);
                    }
                }
            }
            //list就是采购和供应商关联查询后,获取的每条数据是否中标
            String sql = "select supp_Quote.*,supp_ReqINVs.souURL,Inventory.iTaxRate from supp_Quote " +
                    "left join supp_ReqINVs on supp_Quote.ReqID=supp_ReqINVs.ReqID " +
                    "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode";
            //查询前端报价子表
            List<BaoJiaClass> lists = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
            for (BaoJiaClass baoJiaClass : lists) {
                for (LinkedData data : list) {
                    if (baoJiaClass.getcInvCode().equals(data.getcInvCode()) &&
                            baoJiaClass.getcCode().equals(data.getcCodePlan()) &&
                            baoJiaClass.getcVenCode().equals(data.getcVenCode())) {
                        baoJiaClass.setcFlag(data.getcFlag());
                        baoJiaClass.setVerifyDate(data.getVerifyDate());
                    }
                }
            }
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", lists);
            return object.toJSONString();
        } else {
            //采购比价单
            String SQL1 = "select HY_DZ_HU_QuoteDetails.cFlag,HY_DZ_HU_QuoteMain.PlanID,HY_DZ_HU_QuoteDetails.cInvCode" +
                    ",HY_DZ_HU_QuoteMain.VerifyDate,HY_DZ_HU_QuoteDetails.cVenCode from HY_DZ_HU_QuoteDetails,HY_DZ_HU_QuoteMain " +
                    "where HY_DZ_HU_QuoteMain.ID = HY_DZ_HU_QuoteDetails.id";
            List<LinkedDatas> linkedDatas = jdbcTemplate.query(SQL1, new Object[]{}, new BeanPropertyRowMapper<>(LinkedDatas.class));
            //供应商报价单
            String SQL2 = "select HY_DZ_HU_ConsultPrices.cCodePlan,HY_DZ_HU_ConsultPrices.cInvCode,HY_DZ_HU_ConsultPrice.cVenCode " +
                    "from HY_DZ_HU_ConsultPrice,HY_DZ_HU_ConsultPrices where HY_DZ_HU_ConsultPrice.ID = HY_DZ_HU_ConsultPrices.ID";
            List<LinkedData> linkedData = jdbcTemplate.query(SQL2, new Object[]{}, new BeanPropertyRowMapper<>(LinkedData.class));
            //循环这两个集合当if条件符合是将cFlag状态存到新的list集合里
            List<LinkedData> list = new ArrayList<>();
            for (LinkedDatas datas : linkedDatas) {
                for (LinkedData data : linkedData) {
                    if (datas.getcInvCode().equals(data.getcInvCode()) &&
                            datas.getPlanID().equals(data.getcCodePlan()) &&
                            datas.getcVenCode().equals(data.getcVenCode())) {
                        data.setcFlag(datas.getcFlag());
                        data.setVerifyDate(datas.getVerifyDate());
                        list.add(data);
                    }
                }
            }
            //list就是采购和供应商关联查询后,获取的每条数据是否中标
            String sql = "select supp_Quote.*,Inventory.iTaxRate from supp_Quote " +
                    "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode " +
                    "where supp_Quote.cPsn_Name=" + username + " and supp_Quote.cCode !='' and supp_Quote.iInvSCost!=0";
            //查询前端报价子表
            List<BaoJiaClass> lists = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
            for (BaoJiaClass baoJiaClass : lists) {
                for (LinkedData data : list) {
                    if (baoJiaClass.getcInvCode().equals(data.getcInvCode()) &&
                            baoJiaClass.getcCode().equals(data.getcCodePlan()) &&
                            baoJiaClass.getcVenCode().equals(data.getcVenCode())) {
                        baoJiaClass.setcFlag(data.getcFlag());
                        baoJiaClass.setVerifyDate(data.getVerifyDate());
                    }
                }
            }
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", lists);
            return object.toJSONString();
        }
    }

    /**
     * 显示所有要查询的数据
     *
     * @return
     */
    @RequestMapping("/ShowAll")
    public String ShowAll() {
        JSONObject object = new JSONObject();
        //采购比价单
        String SQL1 = "select HY_DZ_HU_QuoteDetails.cFlag,HY_DZ_HU_QuoteMain.PlanID,HY_DZ_HU_QuoteDetails.cInvCode" +
                ",HY_DZ_HU_QuoteMain.VerifyDate,HY_DZ_HU_QuoteDetails.cVenCode from HY_DZ_HU_QuoteDetails,HY_DZ_HU_QuoteMain " +
                "where HY_DZ_HU_QuoteMain.ID = HY_DZ_HU_QuoteDetails.id";
        List<LinkedDatas> linkedDatas = jdbcTemplate.query(SQL1, new Object[]{}, new BeanPropertyRowMapper<>(LinkedDatas.class));
        //供应商报价单
        String SQL2 = "select HY_DZ_HU_ConsultPrices.cCodePlan,HY_DZ_HU_ConsultPrices.cInvCode,HY_DZ_HU_ConsultPrice.cVenCode " +
                "from HY_DZ_HU_ConsultPrice,HY_DZ_HU_ConsultPrices where HY_DZ_HU_ConsultPrice.ID = HY_DZ_HU_ConsultPrices.ID";
        List<LinkedData> linkedData = jdbcTemplate.query(SQL2, new Object[]{}, new BeanPropertyRowMapper<>(LinkedData.class));
        //循环这两个集合当if条件符合是将cFlag状态存到新的list集合里
        List<LinkedData> list = new ArrayList<>();
        for (LinkedDatas datas : linkedDatas) {
            for (LinkedData data : linkedData) {
                if (datas.getcInvCode().equals(data.getcInvCode()) &&
                        datas.getPlanID().equals(data.getcCodePlan()) &&
                        datas.getcVenCode().equals(data.getcVenCode())) {
                    data.setcFlag(datas.getcFlag());
                    data.setVerifyDate(datas.getVerifyDate());
                    list.add(data);
                }
            }
        }
        //list就是采购和供应商关联查询后,获取的每条数据是否中标
        String sql = "select distinct supp_Quote.*,Inventory.iTaxRate from supp_Quote " +
                "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode " +
                "where supp_Quote.cCode !='' and supp_Quote.iInvSCost!=0";
        //查询前端报价子表
        List<BaoJiaClass> lists = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
        for (BaoJiaClass baoJiaClass : lists) {
            for (LinkedData data : list) {
                if (baoJiaClass.getcInvCode().equals(data.getcInvCode()) &&
                        baoJiaClass.getcCode().equals(data.getcCodePlan()) &&
                        baoJiaClass.getcVenCode().equals(data.getcVenCode())) {
                    baoJiaClass.setcFlag(data.getcFlag());
                    baoJiaClass.setVerifyDate(data.getVerifyDate());
                }
            }
        }
        object.put("code", 0);
        object.put("msg", "");
        object.put("data", lists);
        return object.toJSONString();
    }

    /**
     * 搜索
     *
     * @param baoJiaClass
     * @return
     */
    @RequestMapping("/findDate")
    public String findDate(BaoJiaClass baoJiaClass, String username, String Admin) {
        String num = Admin.substring(1, Admin.length() - 1);
        JSONObject object = new JSONObject();
        String date = baoJiaClass.getPubDate();
        String[] dates = date.split(" ");
        if (num.equals("0")) {
            //采购比价单
            String SQL1 = "select HY_DZ_HU_QuoteDetails.cFlag,HY_DZ_HU_QuoteMain.PlanID,HY_DZ_HU_QuoteDetails.cInvCode" +
                    ",HY_DZ_HU_QuoteMain.VerifyDate,HY_DZ_HU_QuoteDetails.cVenCode from HY_DZ_HU_QuoteDetails,HY_DZ_HU_QuoteMain " +
                    "where HY_DZ_HU_QuoteMain.ID = HY_DZ_HU_QuoteDetails.id";
            List<LinkedDatas> linkedDatas = jdbcTemplate.query(SQL1, new Object[]{}, new BeanPropertyRowMapper<>(LinkedDatas.class));
            //供应商报价单
            String SQL2 = "select HY_DZ_HU_ConsultPrices.cCodePlan,HY_DZ_HU_ConsultPrices.cInvCode,HY_DZ_HU_ConsultPrice.cVenCode " +
                    "from HY_DZ_HU_ConsultPrice,HY_DZ_HU_ConsultPrices where HY_DZ_HU_ConsultPrice.ID = HY_DZ_HU_ConsultPrices.ID";
            List<LinkedData> linkedData = jdbcTemplate.query(SQL2, new Object[]{}, new BeanPropertyRowMapper<>(LinkedData.class));
            //循环这两个集合当if条件符合是将cFlag状态存到新的list集合里
            List<LinkedData> list = new ArrayList<>();
            for (LinkedDatas datas : linkedDatas) {
                for (LinkedData data : linkedData) {
                    if (datas.getcInvCode().equals(data.getcInvCode()) &&
                            datas.getPlanID().equals(data.getcCodePlan()) &&
                            datas.getcVenCode().equals(data.getcVenCode())) {
                        data.setcFlag(datas.getcFlag());
                        data.setVerifyDate(datas.getVerifyDate());
                        list.add(data);
                    }
                }
            }
            //list就是采购和供应商关联查询后,获取的每条数据是否中标
            String sql = "select distinct supp_Quote.*,Inventory.iTaxRate from supp_Quote " +
                    "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode " +
                    "where 1=1";

            if (baoJiaClass.getcCode() != "") {
                sql = sql + " and supp_Quote.cCode='" + baoJiaClass.getcCode() + "'";
            }

            if (baoJiaClass.getcVenName() != "") {
                sql = sql + " and supp_Quote.cVenName='" + baoJiaClass.getcVenName() + "'";
            }

            if (baoJiaClass.getcInvCode() != "") {
                sql = sql + " and supp_Quote.cInvCode='" + baoJiaClass.getcInvCode() + "'";
            }

            if (baoJiaClass.getPubDate() != "") {
                sql = sql + " and supp_Quote.pubDate>='" + dates[0] + "' and supp_Quote.pubDate<='" + dates[2] + "'";
            }

            sql = sql + " and supp_Quote.cCode !='' and supp_Quote.iInvSCost!=0";

            //查询前端报价子表
            List<BaoJiaClass> lists = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
            for (BaoJiaClass baoJiaClas : lists) {
                for (LinkedData data : list) {
                    if (baoJiaClas.getcInvCode().equals(data.getcInvCode()) &&
                            baoJiaClas.getcCode().equals(data.getcCodePlan()) &&
                            baoJiaClas.getcVenCode().equals(data.getcVenCode())) {
                        baoJiaClas.setcFlag(data.getcFlag());
                        baoJiaClas.setVerifyDate(data.getVerifyDate());
                    }
                }
            }
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", lists);
            return object.toJSONString();
        } else {
            //采购比价单
            String SQL1 = "select HY_DZ_HU_QuoteDetails.cFlag,HY_DZ_HU_QuoteMain.PlanID,HY_DZ_HU_QuoteDetails.cInvCode" +
                    ",HY_DZ_HU_QuoteMain.VerifyDate,HY_DZ_HU_QuoteDetails.cVenCode from HY_DZ_HU_QuoteDetails,HY_DZ_HU_QuoteMain " +
                    "where HY_DZ_HU_QuoteMain.ID = HY_DZ_HU_QuoteDetails.id";
            List<LinkedDatas> linkedDatas = jdbcTemplate.query(SQL1, new Object[]{}, new BeanPropertyRowMapper<>(LinkedDatas.class));
            //供应商报价单
            String SQL2 = "select HY_DZ_HU_ConsultPrices.cCodePlan,HY_DZ_HU_ConsultPrices.cInvCode,HY_DZ_HU_ConsultPrice.cVenCode " +
                    "from HY_DZ_HU_ConsultPrice,HY_DZ_HU_ConsultPrices where HY_DZ_HU_ConsultPrice.ID = HY_DZ_HU_ConsultPrices.ID";
            List<LinkedData> linkedData = jdbcTemplate.query(SQL2, new Object[]{}, new BeanPropertyRowMapper<>(LinkedData.class));
            //循环这两个集合当if条件符合是将cFlag状态存到新的list集合里
            List<LinkedData> list = new ArrayList<>();
            for (LinkedDatas datas : linkedDatas) {
                for (LinkedData data : linkedData) {
                    if (datas.getcInvCode().equals(data.getcInvCode()) &&
                            datas.getPlanID().equals(data.getcCodePlan()) &&
                            datas.getcVenCode().equals(data.getcVenCode())) {
                        data.setcFlag(datas.getcFlag());
                        data.setVerifyDate(datas.getVerifyDate());
                        list.add(data);
                    }
                }
            }
            //list就是采购和供应商关联查询后,获取的每条数据是否中标
            String sql = "select distinct supp_Quote.*,Inventory.iTaxRate from supp_Quote " +
                    "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode " +
                    "where 1=1";

            if (baoJiaClass.getcCode() != "") {
                sql = sql + " and supp_Quote.cCode='" + baoJiaClass.getcCode() + "'";
            }

            if (baoJiaClass.getcVenName() != "") {
                sql = sql + " and supp_Quote.cVenName='" + baoJiaClass.getcVenName() + "'";
            }

            if (baoJiaClass.getcInvCode() != "") {
                sql = sql + " and supp_Quote.cInvCode='" + baoJiaClass.getcInvCode() + "'";
            }

            if (baoJiaClass.getPubDate() != "") {
                sql = sql + " and supp_Quote.pubDate>='" + dates[0] + "' and supp_Quote.pubDate<='" + dates[2] + "'";
            }

            sql = sql + " and supp_Quote.cPsn_Name=" + username + " and supp_Quote.iInvSCost!=0 and supp_Quote.cCode !=''";

            //查询前端报价子表
            List<BaoJiaClass> lists = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
            for (BaoJiaClass baoJiaClas : lists) {
                for (LinkedData data : list) {
                    if (baoJiaClas.getcInvCode().equals(data.getcInvCode()) &&
                            baoJiaClas.getcCode().equals(data.getcCodePlan()) &&
                            baoJiaClas.getcVenCode().equals(data.getcVenCode())) {
                        baoJiaClas.setcFlag(data.getcFlag());
                        baoJiaClas.setVerifyDate(data.getVerifyDate());
                    }
                }
            }
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", lists);
            return object.toJSONString();
        }


    }


    /**
     * 单个审核
     *
     * @param baoJiaClass
     * @return
     */
    @RequestMapping("/writeBackQuotation")
    public String writeBackQuotation(@RequestBody BaoJiaClass baoJiaClass) {
        JSONObject object = new JSONObject();

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println("日期：" + date);
        String string = "select * from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' and dDate='"
                + date + "' and cMaker='" + baoJiaClass.getcPsn_Name() + "'";
        List<BaoJiaClass> list = jdbcTemplate.query(string, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
        System.out.println("取值长度：" + list.size());

        try {
            //单据号为空时
            if (baoJiaClass.getcCode() == null) {
                String sql = "update supp_Quote set status='2' where cVenName='" + baoJiaClass.getcVenName() + "'" +
                        "and cInvName='" + baoJiaClass.getcInvName() + "' and cInvStd='" + baoJiaClass.getcInvStd()
                        + "' and historyStatus='0'";
                jdbcTemplate.update(sql);
                object.put("code", 0);
                object.put("msg", "审核成功");
                return object.toJSONString();
            } else {
                //单据号不为空，但规格为空时
                if (baoJiaClass.getcInvStd().equals("")) {
                    int finalreqid = 1;
                    System.out.println("公共部分开始--------------------------------------------------------------------------");
                    System.out.println();
                    //税率，根据选取的物料进行不同的税率计算
                    String iTaxRate = this.jdbcTemplate.queryForObject(
                            "select iTaxRate from Inventory where cInvCode = '" + baoJiaClass.getcInvCode() + "'", String.class);
                    System.out.println("税率：" + iTaxRate);
                    //关联单据号
                    String IDPlan = this.jdbcTemplate.queryForObject(
                            "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJiaClass.getcCode() + "' " +
                                    "and cInvName='" + baoJiaClass.getcInvName() + "' and cInvstd='' and cInvCode='" +
                                    baoJiaClass.getcInvCode() + "'", String.class);
                    System.out.println("关联单据号：" + IDPlan);

                    System.out.println();
                    System.out.println("公共部分结束--------------------------------------------------------------------------");
                    System.out.println();
                    if (list.size() != 0) {
                        System.out.println("子表开始--------------------------------------------------");
                        System.out.println();
                        String AutoID = this.jdbcTemplate.queryForObject(
                                "select max(AutoID) from HY_DZ_HU_ConsultPrices", String.class);
                        if (AutoID == null) {
                            AutoID = "2000000001";
                        } else {
                            String panduan = AutoID.substring(0, 1);
                            if (panduan.equals("1")) {
                                AutoID = "2000000001";
                            } else {
                                AutoID = String.valueOf((Integer.parseInt(AutoID) + 1));
                            }
                        }
                        System.out.println("子表1：" + AutoID);

                        String ID = this.jdbcTemplate.queryForObject(
                                "select ID from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' and dDate='" + date + "'", String.class);
                        System.out.println("子表2：" + ID);

                        String cCodeZ = this.jdbcTemplate.queryForObject(
                                "select cCode from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' and dDate='" + date + "'", String.class);
                        System.out.println("子表3：" + cCodeZ);

                        //无含税单价的运算
                        String str = baoJiaClass.getiInvSCost() + "-(" + baoJiaClass.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";
                        //四舍五入
                        DecimalFormat df = new DecimalFormat("#.000000");
                        Float iUnitPrice = Float.valueOf(df.format(jse.eval(str)));
                        System.out.println("子表4：" + iUnitPrice);

                        String Remarks = "MuJuPrice:" + baoJiaClass.getiModSCost();

                        //获取行条码
                        String csysbarcode = "||puxj|" + cCodeZ;
                        //获取行条码
                        String selectreqid = "select MAX(cbsysbarcode) from HY_DZ_HU_ConsultPrices where cCodeZ='" + cCodeZ + "'";
                        String cbsy = csysbarcode + "|";
                        try {
                            String maxreqid = jdbcTemplate.queryForObject(selectreqid, String.class);
                            finalreqid = (Integer.parseInt(maxreqid.substring(maxreqid.length() - 1)) + 1);
                            cbsy = cbsy + finalreqid;
                        } catch (Exception ex) {
//                        ex.printStackTrace();
                            cbsy = cbsy + 1;
                        }
                        System.out.println("子表5：" + cbsy);

                        String sql6 = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJiaClass.getcCode()
                                + "'," + iTaxRate + ",'" + baoJiaClass.getcInvCode() + "',1000024365," + baoJiaClass.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJiaClass.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJiaClass.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql6);

                        String sql7 = "update supp_Quote set iInvSCost=" + baoJiaClass.getiInvSCost() + ",iModSCost="
                                + baoJiaClass.getiModSCost() + ",status='4' where cVenName='" + baoJiaClass.getcVenName() + "' " +
                                "and cCode='" + baoJiaClass.getcCode() + "' and cInvName='" + baoJiaClass.getcInvName() + "' " +
                                "and cInvStd='' and historyStatus=0";
                        jdbcTemplate.update(sql7);
                    } else {
                        System.out.println("主表开始--------------------------------------------------");
                        System.out.println();
                        //取数据库该字段的最大值，并加一
                        String Max_Num = "select max(ID) from HY_DZ_HU_ConsultPrice";
                        String ID = this.jdbcTemplate.queryForObject(Max_Num, String.class);
                        if (ID == null) {
                            ID = "2000000001";
                        } else {
                            String panduan = ID.substring(0, 1);
                            if (panduan.equals("1")) {
                                ID = "2000000001";
                            } else {
                                ID = String.valueOf((Integer.parseInt(ID) + 1));
                            }
                        }
                        System.out.println("主表1：" + ID);

                        String Max_Vlue = "select max(cCode) from HY_DZ_HU_ConsultPrice;";
                        System.out.println("Max_Vlue:" + Max_Vlue);
                        String cCodes = this.jdbcTemplate.queryForObject(
                                Max_Vlue, String.class);
                        System.out.println("cCodes:" + cCodes);
                        if (cCodes == null) {
                            cCodes = "0000000001";
                        } else {
                            cCodes = String.valueOf(Integer.parseInt(cCodes) + 1);
                            switch (cCodes.length()) {
                                case 1:
                                    cCodes = "000000000" + cCodes;
                                    break;
                                case 2:
                                    cCodes = "00000000" + cCodes;
                                    break;
                                case 3:
                                    cCodes = "0000000" + cCodes;
                                    break;
                                case 4:
                                    cCodes = "000000" + cCodes;
                                    break;
                                case 5:
                                    cCodes = "00000" + cCodes;
                                    break;
                                case 6:
                                    cCodes = "0000" + cCodes;
                                    break;
                                case 7:
                                    cCodes = "000" + cCodes;
                                    break;
                                case 8:
                                    cCodes = "00" + cCodes;
                                    break;
                                case 9:
                                    cCodes = "0" + cCodes;
                                    break;
                                case 10:
                                    cCodes = cCodes;
                                    break;
                            }
                        }

                        System.out.println("主表2：" + cCodes);

                        //查询供应商的联系人，从供应商档案里进行查询
                        String sql = "select cVenPerson from Vendor where cVenName = '" + baoJiaClass.getcVenName() + "'";
                        String cVenPerson = this.jdbcTemplate.queryForObject(sql, String.class);
                        System.out.println("主表3：" + cVenPerson);

                        //查询供应商的电话，从供应商档案里进行查询
                        String sql2 = "select cVenPhone from Vendor where cVenName = '" + baoJiaClass.getcVenName() + "'";
                        String cVenPhone = this.jdbcTemplate.queryForObject(sql2, String.class);
                        System.out.println("主表4：" + cVenPhone);

                        //币种
//                        String sql3 = "select cexch_name from HY_DZ_HU_ConsultPrice where cCode='0000000001'";
//                        String cexch_name = this.jdbcTemplate.queryForObject(sql3, String.class);
//                        System.out.println("主表5：" + cexch_name);

                        //行条码
                        String csysbarcode = "||puxj|" + cCodes;
                        System.out.println("主表6：" + csysbarcode);

                        //获取制单人
                        String sql4 = "select Person.cPersonCode from Person " +
                                "where Person.cPersonName='" + baoJiaClass.getcPsn_Name() + "'";
                        String cHandler = this.jdbcTemplate.queryForObject(sql4, String.class);
                        System.out.println(sql4);
                        System.out.println("主表7：" + cHandler);

                        //插入到供应商报价单中
                        String sql5 = "insert into HY_DZ_HU_ConsultPrice(ID, cCode, cVenCode, cVenPerson, cVenPhone, cVenPhone2, cVenFax, cVenHand, cexch_name, nflat, iTaxRate, cHandler," +
                                " dDate, cMaker, cDate, cVerifier, dVeriDate, cState, Remarks, cFlag, CreatePO, iswfcontrolled, ireturncount, iverifystate, cHandleDate, iverifystateex" +
                                ", Transfer, VerifyDate, cverfier, cdefine1, cdefine2, cdefine3, cdefine4, cdefine5, cdefine6, cdefine7, cdefine8, cdefine9, cdefine10, cdefine11" +
                                ", cdefine12, cdefine13, cdefine14, cdefine15, cdefine16, ivtid, iPrintCount, csysbarcode, cCurrentAuditor) VALUES (" + ID + ",'" + cCodes + "','"
                                + baoJiaClass.getcVenCode() + "','" + cVenPerson + "','" + cVenPhone + "','','','" + cVenPhone + "','人民币',1," + iTaxRate + ",'" + cHandler + "','"
                                + date + "','" + baoJiaClass.getcPsn_Name() + "','" + date + "',NULL" + ",NULL,0,'',0,'',0,0,0,'',0,'',NULL,'','','','','',0,'',0,'','','','','','','',0,0,131057,0,'" + csysbarcode
                                + "',NULL)";
                        jdbcTemplate.update(sql5);
                        System.out.println();
                        System.out.println("主表结束--------------------------------------------------");
                        System.out.println();

                        System.out.println("子表开始--------------------------------------------------");
                        System.out.println();
                        //将信息传入到供应商报价的子表里
                        String cVenCode = this.jdbcTemplate.queryForObject(
                                "select cVenCode from HY_DZ_HU_ConsultPrice where ID='" + ID + "'", String.class);
                        System.out.println("子表1：" + cVenCode);

                        String AutoID = this.jdbcTemplate.queryForObject(
                                "select max(AutoID) from HY_DZ_HU_ConsultPrices", String.class);
                        if (AutoID == null) {
                            AutoID = "2000000001";
                        } else {
                            String panduan = AutoID.substring(0, 1);
                            if (panduan.equals("1")) {
                                AutoID = "2000000001";
                            } else {
                                AutoID = String.valueOf((Integer.parseInt(AutoID) + 1));
                            }
                        }
                        System.out.println("子表2：" + AutoID);

                        String ID1 = this.jdbcTemplate.queryForObject(
                                "select ID from HY_DZ_HU_ConsultPrice where cVenCode='" + cVenCode + "' and dDate='" + date + "'", String.class);
                        System.out.println("子表3：" + ID1);

                        String cCodeZ = this.jdbcTemplate.queryForObject(
                                "select cCode from HY_DZ_HU_ConsultPrice where cVenCode='" + cVenCode + "' and dDate='" + date + "'", String.class);
                        System.out.println("子表4：" + cCodeZ);

                        //无含税单价的运算
                        String str = baoJiaClass.getiInvSCost() + "-(" + baoJiaClass.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";
                        //四舍五入
                        DecimalFormat df = new DecimalFormat("#.000000");
                        Float iUnitPrice = Float.valueOf(df.format(jse.eval(str)));
                        System.out.println("子表5：" + iUnitPrice);

                        String Remarks = "MuJuPrice:" + baoJiaClass.getiModSCost();

                        //获取行条码
                        String selectreqid = "select MAX(cbsysbarcode) from HY_DZ_HU_ConsultPrices where cCodeZ='" + cCodeZ + "'";
                        String cbsy = csysbarcode + "|";
                        try {
                            String maxreqid = jdbcTemplate.queryForObject(selectreqid, String.class);
                            finalreqid = (Integer.parseInt(maxreqid.substring(maxreqid.length() - 1)) + 1);
                            cbsy = cbsy + finalreqid;
                        } catch (Exception ex) {
//                        ex.printStackTrace();
                            cbsy = cbsy + 1;
                        }
                        System.out.println("子表6：" + cbsy);

                        String sql6 = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID1 + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJiaClass.getcCode()
                                + "'," + iTaxRate + ",'" + baoJiaClass.getcInvCode() + "',1000024365," + baoJiaClass.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJiaClass.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJiaClass.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql6);

                        String sql7 = "update supp_Quote set iInvSCost=" + baoJiaClass.getiInvSCost() + ",iModSCost="
                                + baoJiaClass.getiModSCost() + ",status='4' where cVenName='" + baoJiaClass.getcVenName() + "' " +
                                "and cCode='" + baoJiaClass.getcCode() + "' and cInvName='" + baoJiaClass.getcInvName() + "' " +
                                "and cInvStd='' and historyStatus=0";
                        jdbcTemplate.update(sql7);
                    }
                    object.put("code", 0);
                    object.put("msg", "审核成功");
                    return object.toJSONString();
                } else {
                    int finalreqid = 1;
                    //税率，根据选取的物料进行不同的税率计算
                    String iTaxRate = this.jdbcTemplate.queryForObject(
                            "select iTaxRate from Inventory where cInvCode = '" + baoJiaClass.getcInvCode() + "'", String.class);
                    System.out.println("税率：" + iTaxRate);

                    String IDPlan = this.jdbcTemplate.queryForObject(
                            "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJiaClass.getcCode() + "' " +
                                    "and cInvName='" + baoJiaClass.getcInvName() + "' and cInvstd='" + baoJiaClass.getcInvStd()
                                    + "'", String.class);
                    System.out.println("关联单据号：" + IDPlan);

                    //规格不为空时
                    if (list.size() != 0) {
                        //将信息传入到供应商报价的子表里
                        String ID = this.jdbcTemplate.queryForObject("select ID from HY_DZ_HU_ConsultPrice " +
                                "where cVenCode='" + baoJiaClass.getcVenCode() + "' and dDate='" + date + "'", String.class);
                        System.out.println("子表1：" + ID);

                        String AutoID = this.jdbcTemplate.queryForObject("select max(AutoID) from HY_DZ_HU_ConsultPrices", String.class);
                        if (AutoID == null) {
                            AutoID = "2000000001";
//                        System.out.println("AutoID的值 : " + AutoID);
                        } else {
                            String panduan = AutoID.substring(0, 1);
                            if (panduan.equals("1")) {
                                AutoID = "2000000001";
                            } else {
                                AutoID = String.valueOf((Integer.parseInt(AutoID) + 1));
                            }
                        }
                        System.out.println("子表2：" + AutoID);

                        String cCodeZ = this.jdbcTemplate.queryForObject(
                                "select cCode from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' and dDate='" + date + "'", String.class);
                        System.out.println("子表3：" + cCodeZ);

                        String csysbarcode = "||puxj|" + cCodeZ;
                        String selectreqid =
                                "select MAX(cbsysbarcode) from HY_DZ_HU_ConsultPrices where cCodeZ='" + cCodeZ + "'";
                        String cbsy = csysbarcode + "|";
                        try {
                            String maxreqid = jdbcTemplate.queryForObject(selectreqid, String.class);
                            finalreqid = (Integer.parseInt(maxreqid.substring(maxreqid.length() - 1)) + 1);
                            cbsy = cbsy + finalreqid;
                        } catch (Exception ex) {
                            cbsy = cbsy + 1;
                        }
                        System.out.println("子表4：" + cbsy);

                        //无含税单价的运算
                        String str = baoJiaClass.getiInvSCost() + "-(" + baoJiaClass.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";
                        //四舍五入
                        DecimalFormat df = new DecimalFormat("#.000000");
                        Float iUnitPrice = Float.valueOf(df.format(jse.eval(str)));
                        System.out.println("子表5：" + iUnitPrice);

                        String Remarks = "MuJuPrice:" + baoJiaClass.getiModSCost();

                        String sql = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJiaClass.getcCode()
                                + "'," + iTaxRate + ",'" + baoJiaClass.getcInvCode() + "',1000024365," + baoJiaClass.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJiaClass.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJiaClass.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql);

                        String sql2 = "update supp_Quote set iInvSCost=" + baoJiaClass.getiInvSCost() + ",iModSCost="
                                + baoJiaClass.getiModSCost() + ",status='4' where cVenName='" + baoJiaClass.getcVenName() + "' " +
                                "and cCode='" + baoJiaClass.getcCode() + "' and cInvName='" + baoJiaClass.getcInvName() + "' and cInvStd='"
                                + baoJiaClass.getcInvStd() + "' and historyStatus='0'";
                        jdbcTemplate.update(sql2);
                    } else {
                        //取数据库该字段的最大值，并加一
                        String Max_Num = "select max(ID) from HY_DZ_HU_ConsultPrice";
                        String ID = this.jdbcTemplate.queryForObject(Max_Num, String.class);
                        if (ID == null) {
                            ID = "2000000001";
                        } else {
                            String panduan = ID.substring(0, 1);
                            if (panduan.equals("1")) {
                                ID = "2000000001";
                            } else {
                                ID = String.valueOf((Integer.parseInt(ID) + 1));
                            }
                        }
                        System.out.println("主表1：" + ID);

                        String Max_Vlue = "select max(cCode) from HY_DZ_HU_ConsultPrice;";
                        System.out.println("Max_Vlue:" + Max_Vlue);
                        String cCodes = this.jdbcTemplate.queryForObject(
                                Max_Vlue, String.class);
                        System.out.println("cCodes:" + cCodes);
                        if (cCodes == null) {
                            cCodes = "0000000001";
                        } else {
                            cCodes = String.valueOf(Integer.parseInt(cCodes) + 1);
                            switch (cCodes.length()) {
                                case 1:
                                    cCodes = "000000000" + cCodes;
                                    break;
                                case 2:
                                    cCodes = "00000000" + cCodes;
                                    break;
                                case 3:
                                    cCodes = "0000000" + cCodes;
                                    break;
                                case 4:
                                    cCodes = "000000" + cCodes;
                                    break;
                                case 5:
                                    cCodes = "00000" + cCodes;
                                    break;
                                case 6:
                                    cCodes = "0000" + cCodes;
                                    break;
                                case 7:
                                    cCodes = "000" + cCodes;
                                    break;
                                case 8:
                                    cCodes = "00" + cCodes;
                                    break;
                                case 9:
                                    cCodes = "0" + cCodes;
                                    break;
                                case 10:
                                    cCodes = cCodes;
                                    break;
                            }
                        }

                        System.out.println("主表2：" + cCodes);

                        //查询供应商的联系人，从供应商档案里进行查询
                        String sql = "select cVenPerson from Vendor where cVenName = '" + baoJiaClass.getcVenName() + "'";
                        String cVenPerson = this.jdbcTemplate.queryForObject(sql, String.class);

                        //查询供应商的电话，从供应商档案里进行查询
                        String sql2 = "select cVenPhone from Vendor where cVenName = '" + baoJiaClass.getcVenName() + "'";
                        String cVenPhone = this.jdbcTemplate.queryForObject(sql2, String.class);

//                        String sql3 = "select cexch_name from HY_DZ_HU_ConsultPrice where cCode='0000000001'";
//                        String cexch_name = this.jdbcTemplate.queryForObject(sql3, String.class);

                        String csysbarcode = "||puxj|" + cCodes;

                        //获取制单人
                        String sql4 = "select distinct Person.cPersonCode from Person left join supp_Quote on supp_Quote.cPsn_Name=Person.cPersonName where Person.cPersonName='"
                                + baoJiaClass.getcPsn_Name() + "'";
                        String cHandler = this.jdbcTemplate.queryForObject(sql4, String.class);

                        //插入到供应商报价单中
                        String sql5 = "insert into HY_DZ_HU_ConsultPrice(ID, cCode, cVenCode, cVenPerson, cVenPhone, cVenPhone2, cVenFax, cVenHand, cexch_name, nflat, iTaxRate, cHandler," +
                                " dDate, cMaker, cDate, cVerifier, dVeriDate, cState, Remarks, cFlag, CreatePO, iswfcontrolled, ireturncount, iverifystate, cHandleDate, iverifystateex" +
                                ", Transfer, VerifyDate, cverfier, cdefine1, cdefine2, cdefine3, cdefine4, cdefine5, cdefine6, cdefine7, cdefine8, cdefine9, cdefine10, cdefine11" +
                                ", cdefine12, cdefine13, cdefine14, cdefine15, cdefine16, ivtid, iPrintCount, csysbarcode, cCurrentAuditor) VALUES (" + ID + ",'" + cCodes + "','"
                                + baoJiaClass.getcVenCode() + "','" + cVenPerson + "','" + cVenPhone + "','','','" + cVenPhone + "','人民币',1," + iTaxRate + ",'" + cHandler + "','"
                                + date + "','" + baoJiaClass.getcPsn_Name() + "','" + date + "',NULL" + ",NULL,0,'',0,'',0,0,0,'',0,'',NULL,'','','','','',0,'',0,'','','','','','','',0,0,131057,0,'"
                                + csysbarcode + "',NULL)";
                        jdbcTemplate.update(sql5);

                        //将信息传入到供应商报价的子表里
                        String cVenCode = this.jdbcTemplate.queryForObject(
                                "select cVenCode from HY_DZ_HU_ConsultPrice where ID='" + ID + "'", String.class);

                        String AutoID = this.jdbcTemplate.queryForObject("select max(AutoID) from HY_DZ_HU_ConsultPrices", String.class);
                        if (AutoID == null) {
                            AutoID = "2000000001";
                        } else {
                            String panduan = AutoID.substring(0, 1);
                            if (panduan.equals("1")) {
                                AutoID = "2000000001";
                            } else {
                                AutoID = String.valueOf((Integer.parseInt(AutoID) + 1));
                            }
                        }

                        String ID1 = this.jdbcTemplate.queryForObject(
                                "select ID from HY_DZ_HU_ConsultPrice where cVenCode='" + cVenCode + "' and dDate='" + date + "'", String.class);

                        String cCodeZ = this.jdbcTemplate.queryForObject(
                                "select cCode from HY_DZ_HU_ConsultPrice where cVenCode='" + cVenCode + "' and dDate='" + date + "'", String.class);

                        //无含税单价的运算
                        String str = "" + baoJiaClass.getiInvSCost() + "-(" + baoJiaClass.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";
                        //四舍五入
                        DecimalFormat df = new DecimalFormat("#.000000");
                        Float iUnitPrice = Float.valueOf(df.format(jse.eval(str)));

                        String Remarks = "MuJuPrice:" + baoJiaClass.getiModSCost();

                        //获取行条码
                        String selectreqid = "select MAX(cbsysbarcode) from HY_DZ_HU_ConsultPrices where cCodeZ='" + cCodeZ + "'";
                        String cbsy = csysbarcode + "|";
                        try {
                            String maxreqid = jdbcTemplate.queryForObject(selectreqid, String.class);
                            finalreqid = (Integer.parseInt(maxreqid.substring(maxreqid.length() - 1)) + 1);
                            cbsy = cbsy + finalreqid;
                        } catch (Exception ex) {
//                        ex.printStackTrace();
                            cbsy = cbsy + 1;
                        }
                        System.out.println("子表6：" + cbsy);

                        String sql6 = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID1 + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJiaClass.getcCode()
                                + "'," + iTaxRate + ",'" + baoJiaClass.getcInvCode() + "',1000024365," + baoJiaClass.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJiaClass.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJiaClass.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql6);

                        String sql7 = "update supp_Quote set iInvSCost=" + baoJiaClass.getiInvSCost() + ",iModSCost="
                                + baoJiaClass.getiModSCost() + ",status='4' where cVenName='" + baoJiaClass.getcVenName() + "' " +
                                "and cCode='" + baoJiaClass.getcCode() + "' and cInvName='" + baoJiaClass.getcInvName() + "' and cInvStd='"
                                + baoJiaClass.getcInvStd() + "' and historyStatus=0";
                        jdbcTemplate.update(sql7);
                    }
                    object.put("code", 0);
                    object.put("msg", "审核成功");
                    return object.toJSONString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            object.put("code", 404);
            object.put("msg", "审核失败");
            return object.toJSONString();
        }
    }

}
