package com.guodian.controller.supplierpage;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.BaoJiaStatus;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 报价
 */
@RestController
@Transactional
public class BaoJiaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 获取报价单的数据信息
     *
     * @return
     */
    @RequestMapping("/quotationList")
    public List<BaoJiaClass> quotationList(String username, String Admin) {
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
                    "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode order by supp_Quote.ReqID asc ";
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
            System.out.println(lists);
            return lists;
        } else if (num.equals("2")) {
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
                    "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode " +
                    "where supp_Quote.cPsn_Name=" + username;
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
            return lists;
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
            String sql = "select supp_Quote.*,supp_ReqINVs.souURL,Inventory.iTaxRate from supp_Quote " +
                    "left join supp_ReqINVs on supp_Quote.ReqID=supp_ReqINVs.ReqID " +
                    "left join Inventory on supp_Quote.cInvCode=Inventory.cInvCode " +
                    "where supp_Quote.cVenName=" + username;
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
            return lists;
        }
    }

    /**
     * 获取报价单的数据信息,当为某一个供应商时
     *
     * @return
     */
    @RequestMapping("/quotation")
    public List<BaoJiaClass> quotation(BaoJiaClass baoJia) {
//        System.out.println("7777：" + baoJia);
        String sql = "select * from supp_Quote where cVenName = '" + baoJia.getcVenName() + "'";
        List<BaoJiaClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
        return list;
    }

    /**
     * 提交物料报价和模具报价
     *
     * @param baoJiaClass
     * @return
     */
    @RequestMapping("/getValues")
    public String getValues(@RequestBody BaoJiaClass baoJiaClass) {
        JSONObject object = new JSONObject();
        String sql = "update supp_Quote set iInvSCost=" + baoJiaClass.getiInvSCost() + ",iModSCost="
                + baoJiaClass.getiModSCost() + ",status='" + BaoJiaStatus.REVIE + "' where cVenName='"
                + baoJiaClass.getcVenName() + "' and cCode='" + baoJiaClass.getcCode() + "' and cInvName='"
                + baoJiaClass.getcInvName() + "' and cInvStd='" + baoJiaClass.getcInvStd() + "' and historyStatus='0'";
        jdbcTemplate.update(sql);
//        System.out.println("报价：" + sql);
        object.put("code", 200);
        object.put("msg", "");
        return object.toJSONString();
    }


}
