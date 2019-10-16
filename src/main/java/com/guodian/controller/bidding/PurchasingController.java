package com.guodian.controller.bidding;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.BaoJiaStatus;
import com.guodian.domin.Status;
import com.guodian.domin.bidding.NewDemandClass;
import com.guodian.domin.bidding.SubmitReleaseClass;
import com.guodian.domin.bidding.SupplierFileClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 采购列表接口
 */
@RestController
@Transactional
public class PurchasingController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 发布新增需求
     * 点击选择招标物料
     * 将数据库的数据展现在页面上
     *
     * @return
     */
    @RequestMapping("/NewDemand")
    public List<NewDemandClass> NewDemand() {
        //查询存货档案中的数据
        String sql = "select " +
                "ComputationUnit.cComUnitName" +
                ",ComputationUnit.cComunitCode" +
                ",Inventory.cInvStd" +
                ",Inventory.cInvName" +
                ",Inventory.cInvCode " +
                "from Inventory " +
                "inner join ComputationUnit " +
                "on ComputationUnit.cComUnitCode=Inventory.cComUnitCode;";
        List<NewDemandClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(NewDemandClass.class));
        return list;
    }

    /**
     * 将供应商表的数据信息传到页面上进行展示
     *
     * @return
     */
    @RequestMapping("/getSupplier")
    public List<SupplierFileClass> getSupplier() {
        String sql = "select * from Vendor";
        List<SupplierFileClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SupplierFileClass.class));
        return list;
    }

    /**
     * 通过部分字段进行查询搜索响应数据
     *
     * @param supplierFileClass
     * @return
     */
    @RequestMapping("/findSupplier")
    public String findSupplier(SupplierFileClass supplierFileClass) {
//        System.out.println(supplierFileClass.getcVenName());
        String sql = "select * from Vendor where cVenName like '%" + supplierFileClass.getcVenName() + "%'";
        List<SupplierFileClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SupplierFileClass.class));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", list);
        return jsonObject.toJSONString();
    }

    /**
     * 获取需求表的数据，将所有的数据展示在
     * 采购列表里
     *
     * @return 需求表数据
     */
    @RequestMapping("/purchasingList")
    public String purchasingList(String username, String Admin) {
//        System.out.println("111.... username：" + username + ",Admin：" + Admin);
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
//        System.out.println(num);
        if (num.equals("0")) {
//            System.out.println(3333);
            //查询报价表数据
            String sql = "select * from supp_ReqINVs order by ReqID asc";
            List<SubmitReleaseClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SubmitReleaseClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", list);
            return object.toJSONString();
        } else if (num.equals("2")) {
//            System.out.println(22222);
            //查询报价表数据
            String sql = "select * from supp_ReqINVs where cPsn_Name=" + username + " order by ReqID asc";
            System.out.println(sql);
            List<SubmitReleaseClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SubmitReleaseClass.class));
            System.out.println(list);
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", list);
            System.out.println(object.toJSONString());
            return object.toJSONString();
        } else {
//            System.out.println(4444);
            //查询报价表数据
            String sql = "select * from supp_ReqINVs where cVenName=" + username + " order by ReqID asc";
            List<SubmitReleaseClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SubmitReleaseClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", list);
            return object.toJSONString();
        }
    }

    /**
     * 提交新需求的发布
     *
     * @param releaseClass
     * @param username
     * @return
     */
    @RequestMapping("/submitRelease")
    public String submitRelease(@RequestBody SubmitReleaseClass releaseClass, String username) {
        System.out.println("releaseClass：" + releaseClass);
        System.out.println("username：" + username);
        System.out.println("-------------------------");

        JSONObject object = new JSONObject();
        try {
            if (releaseClass.getcInvStd().equals("")) {
                //查询存货档案的存货Name
                String cInvName = this.jdbcTemplate.queryForObject(
                        "select cInvName from Inventory where cInvCode='" + releaseClass.getcInvCode() + "'", String.class);
                System.out.println("存货Name：" + cInvName);

                //查询存货档案的最新成本
                String iInvRCost = this.jdbcTemplate.queryForObject(
                        "select iInvNCost from Inventory where cInvCode='" + releaseClass.getcInvCode() + "'", String.class);
                System.out.println("最新成本：" + iInvRCost);

                //生成发布日期（日期为当天发布新需求的日期）
                String pubDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                //查询计量单位编号
                String cComUnitCode = this.jdbcTemplate.queryForObject(
                        "select cComUnitCode from Inventory where cInvStd='' and cInvName='" + cInvName + "' and cInvCode='"
                                + releaseClass.getcInvCode() + "'", String.class);
                System.out.println("计量单位编号：" + cComUnitCode);

                //自动生成需求跟踪号,取reqid的最大值
                String selectreqid = "select MAX(ReqID) from supp_ReqINVs";
                //设置初始值为1
                int finalreqid = 1;
                try {
                    String maxreqid = jdbcTemplate.queryForObject(selectreqid, String.class);
                    finalreqid = (Integer.parseInt(maxreqid.substring(maxreqid.length() - 4)) + 1);
                } catch (Exception ex) {
                    finalreqid = 1;
                }
                String time = new SimpleDateFormat("yyMMdd").format(new Date());
                long reqid = Long.parseLong((time)) * 10000;
                reqid += finalreqid;

                //插入都需求表里
                String sql4 = "insert into supp_ReqINVs(ReqID, cInvCode, cInvName, iQuantity, iInvRCost, cVenCode, cVenName, pubDate, getDate, outDate, iInvSCost, iModSCost, iSUMCost" +
                        ", pVenName, imgURL, souURL, memo, cComunitCode, cInvStd, cComUnitName, cCode, cPsn_Name, mould,status) values (" + reqid + ",'" + releaseClass.getcInvCode() + "','" + cInvName + "'," +
                        releaseClass.getiQuantity() + "," + iInvRCost + ",'','','" + pubDate + "','" + releaseClass.getGetDate() + "','" + releaseClass.getOutDate() + "','','','','" + releaseClass.getpVenName()
                        + "','" + releaseClass.getImgURL() + "','" + releaseClass.getSouURL() + "','" + releaseClass.getMemo() + "','" + cComUnitCode + "','','" + releaseClass.getcComUnitName() + "',''," + username
                        + ",'" + releaseClass.getMould() + "','" + Status.SUBMIT + "')";
                jdbcTemplate.update(sql4);

                //将供应商进行拆分
                String[] str = releaseClass.getpVenName().split(",");
                for (int i = 0; i < str.length; i++) {
                    String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    String sql5 = "insert into supp_Quote(ReqID, cInvCode, cInvName, iQuantity, pubDate, getDate, outDate, iInvSCost, iModSCost, cInvStd, cComUnitName" +
                            ", cComunitCode, cVenCode, cVenName, iSUMCost, memo, imgURL, cCode, cPsn_Name, mould, status, history, creatTime, historyStatus, Download) " +
                            "values (" + reqid + ",'" + releaseClass.getcInvCode() + "','" + cInvName + "'," + releaseClass.getiQuantity() + ",'" + pubDate + "','" + releaseClass.getGetDate()
                            + "','" + releaseClass.getOutDate() + "','','','','" + releaseClass.getcComUnitName() + "','" + cComUnitCode + "','" +
                            StringUtils.substringAfter(str[i], "|") + "','" + StringUtils.substringBefore(str[i], "|") + "','','" + releaseClass.getMemo() + "','" +
                            releaseClass.getImgURL() + "',''," + username + ",'" + releaseClass.getMould() + "','" + BaoJiaStatus.OFFER + "','','" + format + "',0,0)";
                    System.out.println(sql5);
                    jdbcTemplate.update(sql5);
                }

                object.put("code", 200);
                object.put("msg", "提交成功");
                return object.toJSONString();
            } else {
                //查询存货档案的存货Name
                String cInvName = this.jdbcTemplate.queryForObject(
                        "select cInvName from Inventory where cInvCode='" + releaseClass.getcInvCode() + "'", String.class);
                System.out.println("存货Name：" + cInvName);

                //查询存货档案的最新成本
                String iInvRCost = this.jdbcTemplate.queryForObject(
                        "select iInvNCost from Inventory where cInvCode='" + releaseClass.getcInvCode() + "'", String.class);
                System.out.println("最新成本：" + iInvRCost);

                //生成发布日期（日期为当天发布新需求的日期）
                String pubDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                //查询计量单位编号
                String cComUnitCode = this.jdbcTemplate.queryForObject(
                        "select cComUnitCode from Inventory where cInvStd='" + releaseClass.getcInvStd() + "' " +
                                "and cInvName='" + cInvName + "'", String.class);
                System.out.println("计量单位编号：" + cComUnitCode);

                //自动生成需求跟踪号,取reqid的最大值
                String selectreqid = "select MAX(ReqID) from supp_ReqINVs";
                //设置初始值为1
                int finalreqid = 1;
                try {
                    String maxreqid = jdbcTemplate.queryForObject(selectreqid, String.class);
                    finalreqid = (Integer.parseInt(maxreqid.substring(maxreqid.length() - 4)) + 1);
                } catch (Exception ex) {
                    finalreqid = 1;
                }
                String time = new SimpleDateFormat("yyMMdd").format(new Date());
                long reqid = Long.parseLong((time)) * 10000;
                reqid += finalreqid;

                //插入都需求表里
                String sql4 = "insert into supp_ReqINVs(ReqID, cInvCode, cInvName, iQuantity, iInvRCost, cVenCode, cVenName, pubDate, getDate, outDate, iInvSCost, iModSCost, iSUMCost" +
                        ", pVenName, imgURL, souURL, memo, cComunitCode, cInvStd, cComUnitName, cCode, cPsn_Name, mould,status) values (" + reqid + ",'" + releaseClass.getcInvCode() + "','" + cInvName + "'," +
                        releaseClass.getiQuantity() + "," + iInvRCost + ",'','','" + pubDate + "','" + releaseClass.getGetDate() + "','" + releaseClass.getOutDate() + "','','','','" + releaseClass.getpVenName()
                        + "','" + releaseClass.getImgURL() + "','" + releaseClass.getSouURL() + "','" + releaseClass.getMemo() + "','" + cComUnitCode + "','" + releaseClass.getcInvStd() + "','" +
                        releaseClass.getcComUnitName() + "',''," + username + ",'" + releaseClass.getMould() + "','" + Status.SUBMIT + "')";
                jdbcTemplate.update(sql4);

                //将供应商进行拆分
                String[] str = releaseClass.getpVenName().split(",");
                for (int i = 0; i < str.length; i++) {
                    String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    String sql5 = "insert into supp_Quote(ReqID, cInvCode, cInvName, iQuantity, pubDate, getDate, outDate, iInvSCost, iModSCost, cInvStd, cComUnitName" +
                            ", cComunitCode, cVenCode, cVenName, iSUMCost, memo, imgURL, cCode, cPsn_Name, mould, status, history, creatTime, historyStatus, Download) " +
                            "values (" + reqid + ",'" + releaseClass.getcInvCode() + "','" + cInvName + "'," + releaseClass.getiQuantity() + ",'" + pubDate + "','" + releaseClass.getGetDate()
                            + "','" + releaseClass.getOutDate() + "','','','" + releaseClass.getcInvStd() + "','" + releaseClass.getcComUnitName() + "','" + cComUnitCode + "','" +
                            StringUtils.substringAfter(str[i], "|") + "','" + StringUtils.substringBefore(str[i], "|") + "','','" + releaseClass.getMemo() + "','" +
                            releaseClass.getImgURL() + "',''," + username + ",'" + releaseClass.getMould() + "','" + BaoJiaStatus.OFFER + "','','" + format + "',0,0)";
                    System.out.println(sql5);
                    jdbcTemplate.update(sql5);
                }

                object.put("code", 200);
                object.put("msg", "提交成功");
                return object.toJSONString();
            }

        } catch (Exception e) {
            object.put("code", 404);
            object.put("msg", "提交失败");
            return object.toJSONString();
        }
    }


    /**
     * 删除
     */
    @RequestMapping("/deleteList")
    public String deleteList(@RequestBody String str) {
//        System.out.println(str);
        JSONObject object2 = new JSONObject();
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(str);
            JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.getString("data"));
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                if (!object.getString("status").equals("2")) {
                    //将删除的询价计划单发布返回到询价计划单里
                    String sql1 = "update HY_DZ_HU_AskPricePlans set cdefine22=NULL where cCode='" +
                            object.getString("cCode") + "' and cInvstd='" + object.getString("cInvStd") + "' and " +
                            "cInvName='" + object.getString("cInvName") + "'";
//                    System.out.println(sql1);
                    jdbcTemplate.update(sql1);

                    //删除需求表里相应的数据
                    String sql2 = "delete from supp_ReqINVs where ReqID = '" + object.getString("reqID") + "'";
                    jdbcTemplate.update(sql2);

                    //删除报价表里相应的数据
                    String sql3 = "delete from supp_Quote where ReqID = '" + object.getString("reqID") + "'";
                    jdbcTemplate.update(sql3);
                }
            }
            object2.put("code", 200);
            object2.put("msg", "");
            return object2.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            object2.put("code", 202);
            object2.put("msg", e.toString());
            return object2.toJSONString();
        }
    }


}
