package com.guodian.controller.bidding;

import com.alibaba.fastjson.JSONArray;
import com.guodian.domin.BaoJiaStatus;
import com.guodian.domin.Status;
import com.guodian.domin.bidding.InquiryQlanClass;
import com.guodian.domin.bidding.SearchConditionsClass;
import com.guodian.domin.bidding.SubmitReleaseClass;
import com.guodian.domin.supplierpage.BaoJiaClass;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 查询询价计划单
 * 并展现在页面上
 */
@RestController
@Transactional
public class InquiryQlanController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 获取询价计划单信息，并
     * 展现在页面上
     *
     * @return 询价计划单所有的数据
     */
    @RequestMapping("/inquiryQlan")
    public String inquiryQlan(String username,String Admin) {
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
        if (num.equals("0")){
            String sql = "Select " +
                    "HY_DZ_HU_AskPricePlans.cInvCode" +
                    ",HY_DZ_HU_AskPricePlans.cInvName" +
                    ",HY_DZ_HU_AskPricePlans.Qty" +
                    ",HY_DZ_HU_AskPricePlans.Ddate" +
                    ",HY_DZ_HU_AskPricePlans.cInvstd" +
                    ",HY_DZ_HU_AskPricePlans.cInvit" +
                    ",HY_DZ_HU_AskPricePlan.cDate" +
                    ",HY_DZ_HU_AskPricePlan.cCode" +
                    ",hr_hi_person.cPsn_Name " +
                    "from HY_DZ_HU_AskPricePlan " +
                    "inner join HY_DZ_HU_AskPricePlans " +
                    "on HY_DZ_HU_AskPricePlans.cCode = HY_DZ_HU_AskPricePlan.cCode " +
                    "left join hr_hi_person on hr_hi_person.cPsn_Num = HY_DZ_HU_AskPricePlan.Chandler " +
                    "where HY_DZ_HU_AskPricePlans.cdefine22 is NULL and HY_DZ_HU_AskPricePlans.cbcloser is null " +
                    "and HY_DZ_HU_AskPricePlans.iverifystate != '0'";
            List<InquiryQlanClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(InquiryQlanClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", list);
            return object.toJSONString();
        }else {
            String sql = "Select " +
                    "HY_DZ_HU_AskPricePlans.cInvCode" +
                    ",HY_DZ_HU_AskPricePlans.cInvName" +
                    ",HY_DZ_HU_AskPricePlans.Qty" +
                    ",HY_DZ_HU_AskPricePlans.Ddate" +
                    ",HY_DZ_HU_AskPricePlans.cInvstd" +
                    ",HY_DZ_HU_AskPricePlans.cInvit" +
                    ",HY_DZ_HU_AskPricePlan.cDate" +
                    ",HY_DZ_HU_AskPricePlan.cCode" +
                    ",hr_hi_person.cPsn_Name " +
                    "from HY_DZ_HU_AskPricePlan " +
                    "inner join HY_DZ_HU_AskPricePlans " +
                    "on HY_DZ_HU_AskPricePlans.cCode = HY_DZ_HU_AskPricePlan.cCode " +
                    "left join hr_hi_person on hr_hi_person.cPsn_Num = HY_DZ_HU_AskPricePlan.Chandler " +
                    "where hr_hi_person.cPsn_Name="+username+" and HY_DZ_HU_AskPricePlans.cdefine22 is NULL " +
                    "and HY_DZ_HU_AskPricePlans.cbcloser is null and HY_DZ_HU_AskPricePlans.iverifystate != '0'";
            List<InquiryQlanClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(InquiryQlanClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", list);
            return object.toJSONString();
        }
    }

    /**
     * 获取要插入的数据，转化对象后，添加到需求表里
     *
     * @param str 接收的json数组
     * @return 插入成功的信息
     */
    @RequestMapping("/addInquirySchedule")
    public String addInquirySchedule(@RequestBody String str, String username, String Admin) {
        com.alibaba.fastjson.JSONObject objects = new com.alibaba.fastjson.JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
        try {
            if (num.equals("0")) {
                System.out.println(str);
                //将获取到的集合赋值在对象里
                JSONObject object = JSONObject.fromObject(str);
                String msg = object.getString("data");
                //将json字符串进行遍历
                List<InquiryQlanClass> list = JSONArray.parseArray(msg, InquiryQlanClass.class);
                for (InquiryQlanClass inquiry : list) {
                    if (inquiry.getcInvstd().equals("")) {
                        String sql3 = "select cComUnitCode from Inventory where cInvStd is null and cInvName='"
                                + inquiry.getcInvName() + "' and cInvCode='" + inquiry.getcInvCode() + "'";
                        String cComUnitCodes = this.jdbcTemplate.queryForObject(sql3, String.class);
//                        System.out.println("cComUnitCodes：" + cComUnitCodes);

                        //自动生成需求跟踪号
                        String selectreqids = "select MAX(ReqID) from supp_ReqINVs";
                        int finalreqids = 1;
                        try {
                            String maxreqids = jdbcTemplate.queryForObject(selectreqids, String.class);
                            finalreqids = (Integer.parseInt(maxreqids.substring(maxreqids.length() - 4)) + 1);
                        } catch (Exception ex) {
                            finalreqids = 1;
                        }
                        String time = new SimpleDateFormat("yyMMdd").format(new Date());
                        long reqids = Long.parseLong((time)) * 10000;
                        reqids += finalreqids;

                        //将获取到的数据插入到需求表中
                        String sql4 = "insert into supp_ReqINVs(ReqID, cInvCode, cInvName, iQuantity, iInvRCost, cVenCode, cVenName, pubDate, getDate, outDate, iInvSCost, iModSCost," +
                                " iSUMCost, pVenName, imgURL, souURL, memo, cComunitCode, cInvStd, cComUnitName, cCode, cPsn_Name, mould,status) values (" + reqids + ",'" + inquiry.getcInvCode() + "'," +
                                "'" + inquiry.getcInvName() + "','" + inquiry.getQty() + "','','','','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','" + inquiry.getDdate() +
                                "','" + inquiry.getDdate() + "','','','','','','','','" + cComUnitCodes + "','','" + inquiry.getcInvit() + "','" + inquiry.getcCode() + "','" +
                                inquiry.getcPsn_Name() + "',''," + Status.EDIT + ")";
                        jdbcTemplate.update(sql4);
//                        System.out.println("询价计划sql4:" + sql4);

                        //选择后，将选择后的数据不显示，通过''/1的方式进行限制
                        String sql5 = "update HY_DZ_HU_AskPricePlans set cdefine22 = '1' where cInvstd='"
                                + inquiry.getcInvstd() + "' and cInvName='" + inquiry.getcInvName() + "'";
                        jdbcTemplate.update(sql5);
                    } else {
                        //获取计量单位编码
                        String sql = "select cComUnitCode from Inventory where cInvStd='" + inquiry.getcInvstd() + "' " +
                                "and cInvName='" + inquiry.getcInvName() + "' and cInvCode='" + inquiry.getcInvCode() + "'";
                        String cComUnitCode = this.jdbcTemplate.queryForObject(sql, String.class);
//                        System.out.println(cComUnitCode);

                        //自动生成需求跟踪号
                        String selectreqid = "select MAX(ReqID) from supp_ReqINVs";
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
                        //将获取到的数据插入到需求表中
                        String sql1 = "insert into supp_ReqINVs(ReqID, cInvCode, cInvName, iQuantity, iInvRCost, cVenCode, cVenName, pubDate, getDate, outDate, iInvSCost, iModSCost," +
                                " iSUMCost, pVenName, imgURL, souURL, memo, cComunitCode, cInvStd, cComUnitName, cCode, cPsn_Name, mould,status) values (" + reqid + ",'" + inquiry.getcInvCode() + "'," +
                                "'" + inquiry.getcInvName() + "','" + inquiry.getQty() + "','','','','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','" + inquiry.getDdate() +
                                "','" + inquiry.getDdate() + "','','','','','','','','" + cComUnitCode + "','" + inquiry.getcInvstd() + "','" + inquiry.getcInvit() + "','" + inquiry.getcCode() + "','" +
                                inquiry.getcPsn_Name() + "',''," + Status.EDIT + ")";
                        jdbcTemplate.update(sql1);
//                        System.out.println("询价计划sql1:" + sql1);

                        //选择后，将选择后的数据不显示，通过''/1的方式进行限制
                        String sql2 = "update HY_DZ_HU_AskPricePlans set cdefine22 = '1' where cInvstd='"
                                + inquiry.getcInvstd() + "' and cInvName='" + inquiry.getcInvName() + "'";
                        jdbcTemplate.update(sql2);
                    }
                }
                objects.put("code", 200);
                objects.put("msg", "");
                return objects.toJSONString();
            } else {
                //将获取到的集合赋值在对象里
                JSONObject object = JSONObject.fromObject(str);
                String msg = object.getString("data");
                //将data的值放入对象中
                List<InquiryQlanClass> list = JSONArray.parseArray(msg, InquiryQlanClass.class);
                //遍历对象
                for (InquiryQlanClass inquiry : list) {
                    if (inquiry.getcInvstd().equals("")) {
                        String sql = "select cComUnitCode from Inventory where cInvStd is null and cInvName='"
                                + inquiry.getcInvName() + "' and cInvCode='" + inquiry.getcInvCode() + "'";
                        String cComUnitCodes = this.jdbcTemplate.queryForObject(sql, String.class);
//                        System.out.println("cComUnitCodes：" + cComUnitCodes);

                        //自动生成需求跟踪号
                        String selectreqid = "select MAX(ReqID) from supp_ReqINVs";
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

                        //将获取到的数据插入到需求表中
                        String sql2 = "insert into supp_ReqINVs(ReqID, cInvCode, cInvName, iQuantity, iInvRCost, cVenCode, cVenName, pubDate, getDate, outDate, iInvSCost, iModSCost," +
                                " iSUMCost, pVenName, imgURL, souURL, memo, cComunitCode, cInvStd, cComUnitName, cCode, cPsn_Name, mould,status) values (" + reqid + ",'" + inquiry.getcInvCode() + "'," +
                                "'" + inquiry.getcInvName() + "','" + inquiry.getQty() + "','','','','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','" + inquiry.getDdate() +
                                "','" + inquiry.getDdate() + "','','','','','','','','" + cComUnitCodes + "','','" + inquiry.getcInvit() + "','" + inquiry.getcCode() + "'," +
                                username + ",''," + Status.EDIT + ")";
                        jdbcTemplate.update(sql2);
//                        System.out.println("添加询价计划单：" + sql2);

                        //选择后，将选择后的数据不显示，通过''/1的方式进行限制
                        String sql3 = "update HY_DZ_HU_AskPricePlans set cdefine22 = '1' where cInvstd='"
                                + inquiry.getcInvstd() + "' and cInvName='" + inquiry.getcInvName() + "'";
                        jdbcTemplate.update(sql3);
                    } else {
                        //获取计量单位编码
                        String sql = "select cComUnitCode from Inventory where cInvStd='" + inquiry.getcInvstd() + "' " +
                                "and cInvName='" + inquiry.getcInvName() + "' and cInvCode='" + inquiry.getcInvCode() + "'";
                        String cComUnitCode = this.jdbcTemplate.queryForObject(sql, String.class);
//                        System.out.println(cComUnitCode);

                        //自动生成需求跟踪号
                        String selectreqid = "select MAX(ReqID) from supp_ReqINVs";
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

                        //将获取到的数据插入到需求表中
                        String sql2 = "insert into supp_ReqINVs(ReqID, cInvCode, cInvName, iQuantity, iInvRCost, cVenCode, cVenName, pubDate, getDate, outDate, iInvSCost, iModSCost," +
                                " iSUMCost, pVenName, imgURL, souURL, memo, cComunitCode, cInvStd, cComUnitName, cCode, cPsn_Name, mould,status) values (" + reqid + ",'" + inquiry.getcInvCode() + "'," +
                                "'" + inquiry.getcInvName() + "','" + inquiry.getQty() + "','','','','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "','" + inquiry.getDdate() +
                                "','" + inquiry.getDdate() + "','','','','','','','','" + cComUnitCode + "','" + inquiry.getcInvstd() + "','" + inquiry.getcInvit() + "','" + inquiry.getcCode() + "'," +
                                username + ",''," + Status.EDIT + ")";
                        jdbcTemplate.update(sql2);
//                        System.out.println("添加询价计划单2：" + sql2);

                        //选择后，将选择后的数据不显示，通过''/1的方式进行限制
                        String sql3 = "update HY_DZ_HU_AskPricePlans set cdefine22 = '1' where cInvstd='"
                                + inquiry.getcInvstd() + "' and cInvName='" + inquiry.getcInvName() + "'";
                        jdbcTemplate.update(sql3);
                    }
                }
                objects.put("code", 200);
                objects.put("msg", "");
                return objects.toJSONString();
            }
        } catch (Exception e) {
            objects.put("code", 202);
            objects.put("msg", e);
            return objects.toJSONString();
        }
    }

    /**
     * 通过点击提交，将数据插入到报价表中，并进行展现
     *
     * @param submit
     * @return
     */
    @RequestMapping("/editSubmit")
    @Transactional
    public String editSubmit(@RequestBody SubmitReleaseClass submit) {
//        System.out.println("提交：" + submit.toString());
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
//        System.out.println("111");
        try {
            if (submit.getpVenName() != "" && submit.getpVenName() != null) {
                if (submit.getcInvStd().equals("")) {
                    //更新平台页面更改的数据
                    String sql = "update supp_ReqINVs set iQuantity=" + submit.getiQuantity() + ",getDate='" + submit.getGetDate() + "',outDate='" + submit.getOutDate() + "',pVenName='" +
                            submit.getpVenName() + "',memo='" + submit.getMemo() + "',imgURL='" + submit.getImgURL() + "',souURL='" + submit.getSouURL() + "',mould='"
                            + submit.getMould() + "',status=" + Status.SUBMIT + " where cCode='" + submit.getcCode() + "' and cInvCode='" + submit.getcInvCode() + "' and "
                            + "cInvStd=''";
                    jdbcTemplate.update(sql);

                    //查询存货档案的存货Name
                    String sql2 = "select cInvName from Inventory where cInvCode='" + submit.getcInvCode() + "'";
                    String cInvName = this.jdbcTemplate.queryForObject(sql2, String.class);
                    //通过单据编号查询ReqID
                    String sql1 = "select ReqID from supp_ReqINVs where cCode='" + submit.getcCode() + "' and cInvName='" + cInvName
                            + "' and cInvStd=''";
//                    System.out.println(sql1);
                    String reqID = this.jdbcTemplate.queryForObject(sql1, String.class);
//                    System.out.println(reqID);

                    //查询制单人
                    String sql3 = "select cPsn_Name from supp_ReqINVs where cCode='" + submit.getcCode() + "' and cInvName='" + cInvName
                            + "' and cInvStd='' and cInvCode='" + submit.getcInvCode() + "'";
                    String cPsn_Name = this.jdbcTemplate.queryForObject(sql3, String.class);

                    //生成发布日期（日期为当天发布新需求的日期）
                    String pubDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    //查询计量单位编号
                    String sql4 = "select cComUnitCode from Inventory where cInvStd is null and cInvName='" + cInvName
                            + "' and cInvCode='" + submit.getcInvCode() + "'";
//                    System.out.println(sql4);
                    String cComUnitCode = this.jdbcTemplate.queryForObject(sql4, String.class);
//                    System.out.println("cComUnitCode：" + cComUnitCode);
                    try {
                        //将供应商进行拆分
                        String[] str = submit.getpVenName().split(",");
                        //遍历供应商，插入到报价表里
                        for (int i = 0; i < str.length; i++) {
                            String sql5 = "insert into supp_Quote(ReqID, cInvCode, cInvName, iQuantity, pubDate, getDate, outDate, iInvSCost, iModSCost, cInvStd, cComUnitName, cComunitCode," +
                                    " cVenCode, cVenName, iSUMCost, memo, imgURL, cCode, cPsn_Name, mould, status,history,creatTime,historyStatus,Download) values (" + reqID + ",'" + submit.getcInvCode() + "','" + cInvName + "'," +
                                    submit.getiQuantity() + ",'" + pubDate + "','" + submit.getGetDate() + "','" + submit.getOutDate() + "','','','','" + submit.getcComUnitName() + "'" +
                                    ",'" + cComUnitCode + "','" + StringUtils.substringAfter(str[i], "|") + "','" + StringUtils.substringBefore(str[i], "|") + "','','" +
                                    submit.getMemo() + "','" + submit.getImgURL() + "','" + submit.getcCode() + "','" + cPsn_Name + "','" + submit.getMould() + "','" + BaoJiaStatus.OFFER + "','','" + pubDate + "','',0)";
                            jdbcTemplate.update(sql5);
                        }
                        object.put("code", 0);
                        object.put("msg", "");
                        return object.toJSONString();
                    } catch (Exception e) {
                        object.put("code", 1);
                        object.put("msg", e.toString());
                        return object.toJSONString();
                    }

                } else {
                    //更新平台页面更改的数据
                    String sql = "update supp_ReqINVs set iQuantity=" + submit.getiQuantity() + ",getDate='" + submit.getGetDate() + "',outDate='" + submit.getOutDate() + "',pVenName='" +
                            submit.getpVenName() + "',memo='" + submit.getMemo() + "',imgURL='" + submit.getImgURL() + "',souURL='" + submit.getSouURL() + "',mould='"
                            + submit.getMould() + "',status=" + Status.SUBMIT + " where cCode='" + submit.getcCode() + "' and cInvCode='" + submit.getcInvCode() + "' and "
                            + "cInvStd='" + submit.getcInvStd() + "'";
                    System.out.println(sql);
                    jdbcTemplate.update(sql);

                    //查询存货档案的存货Name
                    String sql2 = "select cInvName from Inventory where cInvCode='" + submit.getcInvCode() + "'";
                    String cInvName = this.jdbcTemplate.queryForObject(sql2, String.class);

                    //通过单据编号查询ReqID
                    String sql1 = "select ReqID from supp_ReqINVs where cCode='" + submit.getcCode() + "' and cInvName='" + cInvName
                            + "' and cInvStd='" + submit.getcInvStd() + "'";
                    String reqID = this.jdbcTemplate.queryForObject(sql1, String.class);
//                    System.out.println(reqID);

                    //查询制单人
                    String sql3 = "select cPsn_Name from supp_ReqINVs where cCode='" + submit.getcCode() + "' and cInvName='" + cInvName
                            + "' and cInvStd='" + submit.getcInvStd() + "' and cInvCode='" + submit.getcInvCode() + "'";
                    String cPsn_Name = this.jdbcTemplate.queryForObject(sql3, String.class);

                    //生成发布日期（日期为当天发布新需求的日期）
                    String pubDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    //查询计量单位编号
                    String sql4 = "select cComUnitCode from Inventory where cInvStd='" + submit.getcInvStd() + "' and cInvName='" + cInvName + "' " +
                            "and cInvCode='" + submit.getcInvCode() + "'";
                    System.out.println(sql4);
                    String cComUnitCode = this.jdbcTemplate.queryForObject(sql4, String.class);


                    //将供应商进行拆分
                    String[] str = submit.getpVenName().split(",");
                    //遍历供应商，插入到报价表里
                    for (int i = 0; i < str.length; i++) {
                        String sql5 = "insert into supp_Quote(ReqID, cInvCode, cInvName, iQuantity, pubDate, getDate, outDate, iInvSCost, iModSCost, cInvStd, cComUnitName, cComunitCode," +
                                " cVenCode, cVenName, iSUMCost, memo, imgURL, cCode, cPsn_Name, mould, status,history,creatTime,historyStatus,Download) values (" + reqID + ",'" + submit.getcInvCode() + "','" + cInvName + "'," +
                                submit.getiQuantity() + ",'" + pubDate + "','" + submit.getGetDate() + "','" + submit.getOutDate() + "','','','" + submit.getcInvStd() + "','" + submit.getcComUnitName() + "'" +
                                ",'" + cComUnitCode + "','" + StringUtils.substringAfter(str[i], "|") + "','" + StringUtils.substringBefore(str[i], "|") + "','','" +
                                submit.getMemo() + "','" + submit.getImgURL() + "','" + submit.getcCode() + "','" + cPsn_Name + "','" + submit.getMould() + "','" + BaoJiaStatus.OFFER + "','','" + pubDate + "','',0)";
                        jdbcTemplate.update(sql5);
                    }
                    object.put("code", 0);
                    object.put("msg", "");
                    return object.toJSONString();
                }
            } else {
                object.put("code", 1);
                object.put("msg", "供应商不为空");
                return object.toJSONString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            object.put("code", 404);
            object.put("msg", e.toString());
            return object.toJSONString();
        }
    }

    /**
     * 通过点击保存，将数据存储更新到需求表中
     *
     * @param submit
     * @return
     */
    @RequestMapping("/editSave")
    public String editSave(@RequestBody SubmitReleaseClass submit) {
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
//        System.out.println("保存：" + submit.toString());
//        System.out.println(submit.getpVenName());
        if (submit.getpVenName() != "" && submit.getpVenName() != null) {
            //更新平台页面更改的数据
            String sql = "update supp_ReqINVs set iQuantity=" + submit.getiQuantity() + ",getDate='" + submit.getGetDate() + "',outDate='" + submit.getOutDate() + "',pVenName='" +
                    submit.getpVenName() + "',memo='" + submit.getMemo() + "',imgURL='" + submit.getImgURL() + "',souURL='" + submit.getSouURL() + "',mould='" + submit.getMould()
                    + "',status=" + Status.SAVE + " where cCode='" + submit.getcCode() + "' and cInvCode='" + submit.getcInvCode() + "' and " + "cInvStd='" + submit.getcInvStd() + "'";
            jdbcTemplate.update(sql);

            object.put("code", 200);
            object.put("msg", "");
            return object.toJSONString();
        } else {
            object.put("code", 202);
            object.put("msg", "供应商为空");
            return object.toJSONString();
        }
    }

    /**
     * 获取要搜索的单据编号
     *
     * @return
     */
    @RequestMapping("/search")
    public String search(String username,String Admin) {
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
        if (num.equals("0")){
            String sql = "Select " +
                    "HY_DZ_HU_AskPricePlans.cInvCode" +
                    ",HY_DZ_HU_AskPricePlans.cInvName" +
                    ",HY_DZ_HU_AskPricePlans.Qty" +
                    ",HY_DZ_HU_AskPricePlans.Ddate" +
                    ",HY_DZ_HU_AskPricePlans.cInvstd" +
                    ",HY_DZ_HU_AskPricePlans.cInvit" +
                    ",HY_DZ_HU_AskPricePlan.cDate" +
                    ",HY_DZ_HU_AskPricePlan.cCode" +
                    ",hr_hi_person.cPsn_Name " +
                    "from HY_DZ_HU_AskPricePlan " +
                    "inner join HY_DZ_HU_AskPricePlans " +
                    "on HY_DZ_HU_AskPricePlans.cCode = HY_DZ_HU_AskPricePlan.cCode " +
                    "left join hr_hi_person on hr_hi_person.cPsn_Num = HY_DZ_HU_AskPricePlan.Chandler " +
                    "where HY_DZ_HU_AskPricePlans.cdefine22 is NULL and HY_DZ_HU_AskPricePlans.cbcloser is null " +
                    "and HY_DZ_HU_AskPricePlans.iverifystate != '0'";
            List<InquiryQlanClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(InquiryQlanClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", list);
            return object.toJSONString();
        }else {
            String sql = "Select " +
                    "HY_DZ_HU_AskPricePlans.cInvCode" +
                    ",HY_DZ_HU_AskPricePlans.cInvName" +
                    ",HY_DZ_HU_AskPricePlans.Qty" +
                    ",HY_DZ_HU_AskPricePlans.Ddate" +
                    ",HY_DZ_HU_AskPricePlans.cInvstd" +
                    ",HY_DZ_HU_AskPricePlans.cInvit" +
                    ",HY_DZ_HU_AskPricePlan.cDate" +
                    ",HY_DZ_HU_AskPricePlan.cCode" +
                    ",hr_hi_person.cPsn_Name " +
                    "from HY_DZ_HU_AskPricePlan " +
                    "inner join HY_DZ_HU_AskPricePlans " +
                    "on HY_DZ_HU_AskPricePlans.cCode = HY_DZ_HU_AskPricePlan.cCode " +
                    "left join hr_hi_person on hr_hi_person.cPsn_Num = HY_DZ_HU_AskPricePlan.Chandler " +
                    "where hr_hi_person.cPsn_Name="+username+" and HY_DZ_HU_AskPricePlans.cdefine22 is NULL " +
                    "and HY_DZ_HU_AskPricePlans.cbcloser is null and HY_DZ_HU_AskPricePlans.iverifystate != '0'";
            List<InquiryQlanClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(InquiryQlanClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("data", list);
            return object.toJSONString();
        }
    }

    /**
     * 询价计划单对应相应的信息进行搜索
     *
     * @param
     * @return 响应结果
     */
    @RequestMapping("/conditionsSearch")
    public String conditionsSearch(SearchConditionsClass searchConditionsClass) {
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
        //根据获取的条件进行查询
        String sql = "Select " +
                "HY_DZ_HU_AskPricePlans.cInvCode" +
                ",HY_DZ_HU_AskPricePlans.cInvName" +
                ",HY_DZ_HU_AskPricePlans.Qty" +
                ",HY_DZ_HU_AskPricePlans.Ddate" +
                ",HY_DZ_HU_AskPricePlans.cInvstd" +
                ",HY_DZ_HU_AskPricePlans.cInvit" +
                ",HY_DZ_HU_AskPricePlan.cDate" +
                ",HY_DZ_HU_AskPricePlan.cCode" +
                ",hr_hi_person.cPsn_Name " +
                "from HY_DZ_HU_AskPricePlan " +
                "inner join HY_DZ_HU_AskPricePlans " +
                "on HY_DZ_HU_AskPricePlans.cCode = HY_DZ_HU_AskPricePlan.cCode " +
                "left join hr_hi_person on hr_hi_person.cPsn_Num = HY_DZ_HU_AskPricePlan.Chandler " +
                "where HY_DZ_HU_AskPricePlans.cdefine22 is NULL and HY_DZ_HU_AskPricePlan.cCode='"
                + searchConditionsClass.getcCode() + "' and HY_DZ_HU_AskPricePlans.cbcloser is null " +
                "and HY_DZ_HU_AskPricePlans.iverifystate != '0'";
        List<SearchConditionsClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SearchConditionsClass.class));
        //返回查询后的结果
        object.put("code", 0);
        object.put("msg", "");
        object.put("data", list);
        return object.toJSONString();
    }

}
