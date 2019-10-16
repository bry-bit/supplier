package com.guodian.controller.bidding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.guodian.domin.supplierpage.BaoJiaClass;
import com.guodian.domin.supplierpage.BaojiaListClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guodian.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@RestController
@Transactional
public class StatusController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String cMaker;
    //String类型的运算
    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    /**
     * 数据退回
     *
     * @param s
     * @return
     */
    @RequestMapping("/testTh")
    @ResponseBody
    @Transactional
    public String test(@RequestBody String s) {
        System.out.println(s);
        //System.out.println("输入 : "+s);
        //将字符串转化为json格式
        s = s.replace("\\", "");
        s = s.replace("'", "\"");
        s = s.substring(1, s.length() - 1);
        System.out.println("转成json串 : " + s);
        BaojiaListClass baojiaListClass = ObjectMapperUtil.toObject(s, BaojiaListClass.class);
        System.out.println("转化 : " + baojiaListClass);
        //退回要将 iInvSCost  iModSCost memo 修改这三个字段  0代表报价  1 代表退回  2 代表审核
        List<BaoJiaClass> list = baojiaListClass.getData();
        //最后返回登录成功
        JSONObject object = new JSONObject();
        try {
            String sqlInsert = "insert into supp_Quote( ReqID,cInvCode,cInvName,iQuantity,pubDate," +
                    "getDate,outDate,cVenName,cVenCode,iInvSCost,iModSCost,iSUMCost,memo" +
                    ",cInvStd,cComUnitName,cPsn_Name,cCode,mould,status,creatTime,historyStatus,history,Download) values(" +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //如果退回 先将这个数据循环修改状态
            for (BaoJiaClass baoJiaClass : list) {
                //修改之前要删除 时间早于 传过来的数据
                String sqlDelete = "delete supp_Quote  where cCode = ?  and cInvCode = ? and cVenName = ?";
                //删除数据
                jdbcTemplate.update(sqlDelete, baoJiaClass.getcCode(), baoJiaClass.getcInvCode(), baoJiaClass.getcVenName());
                baoJiaClass.setStatus("3");
                Date data = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String format2 = format.format(data);
                jdbcTemplate.update(sqlInsert, baoJiaClass.getReqID(), baoJiaClass.getcInvCode(), baoJiaClass.getcInvName(), baoJiaClass.getiQuantity(),
                        baoJiaClass.getPubDate(), baoJiaClass.getGetDate(), baoJiaClass.getOutDate(), baoJiaClass.getcVenName(), baoJiaClass.getcVenCode(),
                        baoJiaClass.getiInvSCost(), baoJiaClass.getiModSCost(), baoJiaClass.getiSUMCost(), baoJiaClass.getMemo(), baoJiaClass.getcInvStd(),
                        baoJiaClass.getcComUnitName(), baoJiaClass.getcPsn_Name(), baoJiaClass.getcCode(), baoJiaClass.getMould(), baoJiaClass.getStatus()
                        , format2, 1, baojiaListClass.getInputText(), 0);
            }
            //在添加数据
            for (BaoJiaClass baoJiaClass : list) {
                baoJiaClass.setiInvSCost(null);
                baoJiaClass.setiModSCost(null);
                baojiaListClass.setInputText(null);
                baoJiaClass.setStatus("0");
                Date data = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String format2 = format.format(data);
                jdbcTemplate.update(sqlInsert, baoJiaClass.getReqID(), baoJiaClass.getcInvCode(), baoJiaClass.getcInvName(), baoJiaClass.getiQuantity(),
                        baoJiaClass.getPubDate(), baoJiaClass.getGetDate(), baoJiaClass.getOutDate(), baoJiaClass.getcVenName(), baoJiaClass.getcVenCode(),
                        baoJiaClass.getiInvSCost(), baoJiaClass.getiModSCost(), baoJiaClass.getiSUMCost(), baoJiaClass.getMemo(), baoJiaClass.getcInvStd(),
                        baoJiaClass.getcComUnitName(), baoJiaClass.getcPsn_Name(), baoJiaClass.getcCode(), baoJiaClass.getMould(), baoJiaClass.getStatus()
                        , format2, 0, baojiaListClass.getInputText(), 0);
            }
            object.put("code", 0);
            object.put("msg", "退回成功");
            System.out.println(object.toJSONString());
            return object.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            object.put("code", 1);
            object.put("msg", "退回失败");
            System.out.println(object.toJSONString());
            return object.toJSONString();
        }
    }

    /**
     * 批量审核
     *
     * @param str
     * @return
     */
    @RequestMapping("/ShenHes")
    public String ShenHes(@RequestBody String str,String username) {
        System.out.println("获取的数据：" + str);
        int finalreqid = 1;
        BaojiaListClass baojiaListClass = ObjectMapperUtil.toObject(str, BaojiaListClass.class);
        JSONObject object = new JSONObject();
        try {
            //将获取的data数据放入BaoJiaClass中
            List<BaoJiaClass> list = baojiaListClass.getData();
//            System.out.println("转换的数据：" + list);
            //用来存无单据号
            List<BaoJiaClass> list1 = new ArrayList<>();
            //用来存有单据号的
            List<BaoJiaClass> list2 = new ArrayList<>();
            //将list集合的数据放入对象中
            for (BaoJiaClass baoJiaClass : list) {
                //判断是否有单据编号
                if (baoJiaClass.getcCode() == null || baoJiaClass.getcCode() == "") {
                    //无，则放入list1集合中
                    list1.add(baoJiaClass);
//                    System.out.println("无单据号集合：" + list1);
                } else if (baoJiaClass.getStatus().equals("1")) {
                    //有，则放入list2集合中
                    list2.add(baoJiaClass);
                    System.out.println("有单据号集合：" + list2);
                }
            }


            //当获取的数据没有单据编号时
            for (BaoJiaClass baoJiaClass : list1) {
                //判断规格是否为空
                if (baoJiaClass.getcInvStd().equals("")) {
                    //则直接修改单据内容信息和状态
                    String updateSQL = "update supp_Quote set status = '2' where cCode = '" + baoJiaClass.getcCode() + "' " +
                            "and cInvCode = '" + baoJiaClass.getcInvCode() + "' and cInvStd = '' ";
                    jdbcTemplate.update(updateSQL);
                } else {
                    //则直接修改单据内容信息和状态
                    String updateSQL = "update supp_Quote set status = '2' where cCode = '" + baoJiaClass.getcCode() + "' " +
                            "and cInvCode = '" + baoJiaClass.getcInvCode() + "' and cInvStd = '" + baoJiaClass.getcInvStd() + "' ";
                    jdbcTemplate.update(updateSQL);
                }
            }


            //单据号不为空时，保留供应商名称
            HashSet<String> hashSet = new HashSet<>();
            //保存重复数据
            HashSet<BaoJiaClass> classHashSet = new HashSet<>();
            //遍历
            for (int i = 0; i < list2.size(); i++) {
                for (int j = 0; j < list2.size(); j++) {
                    if (j == i) {
                        continue;
                    } else if (list2.get(j).getPubDate().equals(list2.get(i).getPubDate())
                            && list2.get(j).getcVenName().equals(list2.get(i).getcVenName())) {
                        classHashSet.add(list2.get(j));
                        hashSet.add(list2.get(i).getcVenName());
                        continue;
                    }
                }
            }
            System.out.println("供应商名称hashSet：" + hashSet);
            System.out.println("保存重复数据：" + classHashSet);


            //遍历供应商名称的集合，存主表
            for (String cVenNames : hashSet) {
                //查询供应商报价单是否有该主表单据
                String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String SQL = "select distinct cPsn_Name from supp_Quote where cVenName='" + cVenNames + "'";
                List<BaoJiaClass> cPsn_Name = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
                System.out.println("业务员名称：" + cPsn_Name.get(0).getcPsn_Name());

                String MAX_SQL = "select distinct HY_DZ_HU_ConsultPrice.* from HY_DZ_HU_ConsultPrice " +
                        "left join supp_Quote on supp_Quote.cVenCode=HY_DZ_HU_ConsultPrice.cVenCode " +
                        "where supp_Quote.cVenName='" + cVenNames + "' and HY_DZ_HU_ConsultPrice.dDate='" + dates + "' " +
                        "and HY_DZ_HU_ConsultPrice.cMaker='" + cPsn_Name.get(0).getcPsn_Name() + "'";
                List<BaoJiaClass> List = jdbcTemplate.query(MAX_SQL, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
                //如果有该条数据
                if (List.size() != 0) {
                    continue;
                } else {
                    //没有该条数据
                    System.out.println("重复第一次存主表---------------------------------");
                    System.out.println("重复的供应商名称：" + cVenNames);
                    //获取ID的最大值
                    String Max_Num = "select max(ID) from HY_DZ_HU_ConsultPrice";
                    String ID = this.jdbcTemplate.queryForObject(Max_Num, String.class);
                    if (ID == null) {
                        ID = "2000000001";
                        System.out.println("ID的值 : " + ID);
                    } else {
                        String panduan = ID.substring(0, 1);
                        if (panduan.equals("1")) {
                            ID = "2000000001";
                        } else {
                            ID = String.valueOf((Integer.parseInt(ID) + 1));
                        }
                    }
                    System.out.println("自增ID：" + ID);

                    //获取cCodes值
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
                    System.out.println("获取的cCodes值：" + cCodes);

                    //查询供应商的电话，从供应商档案里进行查询
                    String cVenPerson = this.jdbcTemplate.queryForObject(
                            "select cVenPerson from Vendor where cVenName = '" + cVenNames + "'", String.class);
                    System.out.println("供应商联系人：" + cVenPerson);

                    //查询供应商的电话，从供应商档案里进行查询
                    String cVenPhone = this.jdbcTemplate.queryForObject(
                            "select cVenPhone from Vendor where cVenName = '" + cVenNames + "'", String.class);
                    System.out.println("供应商的电话：" + cVenPhone);

                    //查询币种
//                    String cexch_name = this.jdbcTemplate.queryForObject(
//                            "select cexch_name from HY_DZ_HU_ConsultPrice where cCode='0000000001'", String.class);
//                    System.out.println("币种：" + cexch_name);

                    //行编号
                    String csysbarcode = "||puxj|" + cCodes;
                    System.out.println("编号：" + csysbarcode);

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    System.out.println("日期：" + date);

                    //查询供应商的电话，从供应商档案里进行查询
                    String cVenCode = this.jdbcTemplate.queryForObject(
                            "select cVenCode from Vendor where cVenName = '" + cVenNames + "'", String.class);
                    System.out.println("供应商编码：" + cVenCode);

                    //制单人
                    String sql = "select distinct cPsn_Name from supp_Quote where cVenName='" + cVenNames + "'";
                    List<BaoJiaClass> cMakers = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
                    cMaker = cMakers.get(0).getcPsn_Name();
                    System.out.println("制单人：" + cMaker);

                    //业务员
                    String cHandler = this.jdbcTemplate.queryForObject(
                            "select distinct Person.cPersonCode from Person " +
                                    "left join supp_Quote on supp_Quote.cPsn_Name=Person.cPersonName " +
                                    "where Person.cPersonName='" + cMaker + "'", String.class);
                    System.out.println("业务员：" + cHandler);

                    //插入到供应商报价单主表中
                    String sql7 = "insert into HY_DZ_HU_ConsultPrice(ID, cCode, cVenCode, cVenPerson, cVenPhone, cVenPhone2, cVenFax, cVenHand, cexch_name, nflat, iTaxRate, cHandler," +
                            " dDate, cMaker, cDate, cVerifier, dVeriDate, cState, Remarks, cFlag, CreatePO, iswfcontrolled, ireturncount, iverifystate, cHandleDate, iverifystateex" +
                            ", Transfer, VerifyDate, cverfier, cdefine1, cdefine2, cdefine3, cdefine4, cdefine5, cdefine6, cdefine7, cdefine8, cdefine9, cdefine10, cdefine11" +
                            ", cdefine12, cdefine13, cdefine14, cdefine15, cdefine16, ivtid, iPrintCount, csysbarcode, cCurrentAuditor) VALUES (" + ID + ",'" + cCodes + "','"
                            + cVenCode + "','" + cVenPerson + "','" + cVenPhone + "','','','" + cVenPhone + "','人民币',1,16,'" + cHandler + "','" + date
                            + "','" + cMaker + "','" + date + "',NULL" + ",NULL,0,'',0,'',0,0,0,'',0,'',NULL,'','','','','',0,'',0,'','','','','','','',0,0,131057,0,'" + csysbarcode
                            + "',NULL)";
                    jdbcTemplate.update(sql7);
                }
            }


            //将重复的数据存入子表
            for (BaoJiaClass baoJiaClass : classHashSet) {
                if (baoJiaClass.getcInvStd().equals("")) {
                    //获取自增AutoID
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
                    System.out.println("获取AutoID的值：" + AutoID);

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    //获取主表ID值
                    String ID = this.jdbcTemplate.queryForObject(
                            "select ID from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' and dDate='" + date + "' and cMaker='" + cMaker + "'", String.class);
                    System.out.println("获取ID的值：" + ID);

                    //获取主表单据编号
                    String cCodeZ = this.jdbcTemplate.queryForObject(
                            "select cCode from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' and dDate='" + date + "' and cMaker='" + cMaker + "'", String.class);
                    System.out.println("获取cCodeZ的值：" + cCodeZ);

                    //税率，根据选取的物料进行不同的税率计算
                    String iTaxRate = this.jdbcTemplate.queryForObject(
                            "select iTaxRate from Inventory where cInvCode = '" + baoJiaClass.getcInvCode() + "'", String.class);
                    System.out.println("税率：" + iTaxRate);

                    //获取行条码
                    String csysbarcode = "||puxj|" + cCodeZ;
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
                    System.out.println("行条码：" + cbsy);

                    //无含税单价的运算
                    String NoiTaxRate = baoJiaClass.getiInvSCost() + "-(" + baoJiaClass.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";
                    System.out.println("无含税单价：" + NoiTaxRate);

                    //四舍五入
                    DecimalFormat df = new DecimalFormat("#.000000");
                    Float iUnitPrice = Float.valueOf(df.format(jse.eval(NoiTaxRate)));
                    System.out.println("原币单价：" + iUnitPrice);

                    //将模具价格写在备注里
                    String Remarks = "MuJuPrice:" + baoJiaClass.getiModSCost();

                    //获取关联单据
                    String IDPlanSQL = "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJiaClass.getcCode() + "' " +
                            "and cInvName='" + baoJiaClass.getcInvName() + "' and cInvCode='" + baoJiaClass.getcInvCode() + "' " +
                            "and cInvstd=''";
                    String IDPlan = this.jdbcTemplate.queryForObject(IDPlanSQL, String.class);
                    System.out.println("关联单据：" + IDPlan);

                    //插入供应商报价单子表中
                    String sql = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                            "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                            " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                            " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJiaClass.getcCode()
                            + "'," + iTaxRate + ",'" + baoJiaClass.getcInvCode() + "',1000024365," + baoJiaClass.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJiaClass.getiInvSCost()
                            + "," + iUnitPrice + "," + baoJiaClass.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                            ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                    jdbcTemplate.update(sql);

                    //将状态改为审核中
                    String sql2 = "update supp_Quote set iInvSCost=" + baoJiaClass.getiInvSCost() + ",iModSCost="
                            + baoJiaClass.getiModSCost() + ",status='4' where cVenName='" + baoJiaClass.getcVenName() + "' " +
                            "and cCode='" + baoJiaClass.getcCode() + "' and cInvName='" + baoJiaClass.getcInvName() + "' " +
                            "and cInvStd='' and historyStatus=0";
//                    jdbcTemplate.update(sql2);
                } else {
                    //获取自增AutoID
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
                    System.out.println("获取AutoID的值：" + AutoID);

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    //获取主表ID值
                    String ID = this.jdbcTemplate.queryForObject(
                            "select ID from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' " +
                                    "and dDate='" + date + "' and cMaker=" + username, String.class);
                    System.out.println(ID);
                    System.out.println("获取ID的值：" + ID);

                    //获取主表单据编号
                    String cCodeZ = this.jdbcTemplate.queryForObject(
                            "select cCode from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJiaClass.getcVenCode() + "' " +
                                    "and dDate='" + date + "' and cMaker=" + username, String.class);
                    System.out.println("获取cCodeZ的值：" + cCodeZ);

                    //税率，根据选取的物料进行不同的税率计算
                    String sql2 = "select iTaxRate from Inventory where cInvCode = '" + baoJiaClass.getcInvCode() + "'";
                    String iTaxRate = this.jdbcTemplate.queryForObject(sql2, String.class);
                    System.out.println("税率：" + iTaxRate);

                    //获取行条码
                    String csysbarcode = "||puxj|" + cCodeZ;
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
                    System.out.println("行条码：" + cbsy);

                    //无含税单价的运算
                    String NoiTaxRate = baoJiaClass.getiInvSCost() + "-(" + baoJiaClass.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";
                    System.out.println("无含税单价：" + NoiTaxRate);

                    //四舍五入
                    DecimalFormat df = new DecimalFormat("#.000000");
                    Float iUnitPrice = Float.valueOf(df.format(jse.eval(NoiTaxRate)));
                    System.out.println("原币单价：" + iUnitPrice);

                    //将模具价格写在备注里
                    String Remarks = "MuJuPrice:" + baoJiaClass.getiModSCost();

                    //获取关联单据
                    String IDPlanSQL = "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJiaClass.getcCode() + "' " +
                            "and cInvName='" + baoJiaClass.getcInvName() + "' and cInvCode='" + baoJiaClass.getcInvCode() + "' " +
                            "and cInvstd='" + baoJiaClass.getcInvStd() + "'";
                    String IDPlan = this.jdbcTemplate.queryForObject(IDPlanSQL, String.class);
                    System.out.println("关联单据：" + IDPlan);

                    //插入供应商报价单子表中
                    String sql = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                            "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                            " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                            " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJiaClass.getcCode()
                            + "'," + iTaxRate + ",'" + baoJiaClass.getcInvCode() + "',1000024365," + baoJiaClass.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJiaClass.getiInvSCost()
                            + "," + iUnitPrice + "," + baoJiaClass.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                            ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                    jdbcTemplate.update(sql);

                    String sql3 = "update supp_Quote set iInvSCost=" + baoJiaClass.getiInvSCost() + ",iModSCost="
                            + baoJiaClass.getiModSCost() + ",status='4' where cVenName='" + baoJiaClass.getcVenName() + "' " +
                            "and cCode='" + baoJiaClass.getcCode() + "' and cInvName='" + baoJiaClass.getcInvName() + "' and cInvStd='"
                            + baoJiaClass.getcInvStd() + "' and historyStatus=0";
                    jdbcTemplate.update(sql3);
                }

            }

            //有单据号但不重复的数据
            for (String string : hashSet) {
                for (int i = 0; i < list2.size(); ) {
                    if (string.equals(list2.get(i).getcVenName())) {
                        list2.remove(i);
                        i = 0;
                    } else {
                        i++;
                    }
                }
            }
            System.out.println("不重复数据：" + list2);

            for (BaoJiaClass baoJia : list2) {
                //查询供应商报价单是否有该主表单据
                String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String MAX_SQL = "select distinct HY_DZ_HU_ConsultPrice.* from HY_DZ_HU_ConsultPrice " +
                        "left join supp_Quote on supp_Quote.cVenCode=HY_DZ_HU_ConsultPrice.cVenCode " +
                        "where supp_Quote.cVenName='" + baoJia.getcVenName() + "' and HY_DZ_HU_ConsultPrice.dDate='" + dates + "' " +
                        "and HY_DZ_HU_ConsultPrice.cMaker='" + baoJia.getcPsn_Name() + "'";
                List<BaoJiaClass> List = jdbcTemplate.query(MAX_SQL, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
                System.out.println(List.size());
                //长度不为零时，只存子表
                if (List.size() != 0) {
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
                    System.out.println("获取AutoID的值：" + AutoID);

                    String ID = this.jdbcTemplate.queryForObject(
                            "select ID from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJia.getcVenCode() + "' " +
                                    "and dDate='" + dates + "' and cMaker='" + baoJia.getcPsn_Name() + "'", String.class);
                    System.out.println("子表主键ID :" + ID);

                    String cCodeZ = this.jdbcTemplate.queryForObject(
                            "select cCode from HY_DZ_HU_ConsultPrice where cVenCode='" + baoJia.getcVenCode() + "' " +
                                    "and dDate='" + dates + "' and cMaker='" + baoJia.getcPsn_Name() + "'", String.class);
                    System.out.println("单据编号：" + cCodeZ);

                    //税率，根据选取的物料进行不同的税率计算
                    String iTaxRate = this.jdbcTemplate.queryForObject(
                            "select iTaxRate from Inventory where cInvCode = '" + baoJia.getcInvCode() + "'", String.class);
                    System.out.println("税率：" + iTaxRate);

                    //行条码
                    String csysbarcode = "||puxj|" + cCodeZ;
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
                    System.out.println("行条码: " + cbsy);

                    //无含税单价的运算
                    String string = baoJia.getiInvSCost() + "-(" + baoJia.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";

                    //四舍五入
                    DecimalFormat df = new DecimalFormat("#.000000");
                    Float iUnitPrice = Float.valueOf(df.format(jse.eval(string)));

                    System.out.println("无税单价：" + iUnitPrice);

                    //备注
                    String Remarks = "MuJuPrice:" + baoJia.getiModSCost();

                    if (baoJia.getcInvStd().equals("")) {
                        //关联单据
                        String IDPlanSQL = "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJia.getcCode() + "' " +
                                "and cInvName='" + baoJia.getcInvName() + "' and cInvCode='" + baoJia.getcInvCode() + "' and cInvstd=''";
                        String IDPlan = this.jdbcTemplate.queryForObject(IDPlanSQL, String.class);
                        System.out.println("关联单据：" + IDPlan);

                        String sql = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJia.getcCode()
                                + "'," + iTaxRate + ",'" + baoJia.getcInvCode() + "',1000024365," + baoJia.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJia.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJia.getiInvSCost() + ",'" + dates + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql);

                        String sql2 = "update supp_Quote set iInvSCost=" + baoJia.getiInvSCost() + ",iModSCost="
                                + baoJia.getiModSCost() + ",status='4' where cVenName='" + baoJia.getcVenName() + "' " +
                                "and cCode='" + baoJia.getcCode() + "' and cInvName='" + baoJia.getcInvName() + "' " +
                                "and cInvStd='' and historyStatus=0";
                        jdbcTemplate.update(sql2);
                    } else {
                        //关联单据
                        String IDPlanSQL = "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJia.getcCode() + "' " +
                                "and cInvName='" + baoJia.getcInvName() + "' and cInvCode='" + baoJia.getcInvCode() + "' " +
                                "and cInvstd='" + baoJia.getcInvStd() + "'";
                        String IDPlan = this.jdbcTemplate.queryForObject(IDPlanSQL, String.class);
                        System.out.println("关联单据：" + IDPlan);

                        String sql = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJia.getcCode()
                                + "'," + iTaxRate + ",'" + baoJia.getcInvCode() + "',1000024365," + baoJia.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJia.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJia.getiInvSCost() + ",'" + dates + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql);

                        String sql2 = "update supp_Quote set iInvSCost=" + baoJia.getiInvSCost() + ",iModSCost="
                                + baoJia.getiModSCost() + ",status='4' where cVenName='" + baoJia.getcVenName() + "' " +
                                "and cCode='" + baoJia.getcCode() + "' and cInvName='" + baoJia.getcInvName() + "' and cInvStd='"
                                + baoJia.getcInvStd() + "' and historyStatus=0";
                        jdbcTemplate.update(sql2);
                    }

                } else {
                    //为零时，创建新的表
                    //获取ID的最大值
                    String Max_Num = "select max(ID) from HY_DZ_HU_ConsultPrice";
                    String ID = this.jdbcTemplate.queryForObject(Max_Num, String.class);
                    if (ID == null) {
                        ID = "2000000001";
                        System.out.println("ID的值 : " + ID);
                    } else {
                        String panduan = ID.substring(0, 1);
                        if (panduan.equals("1")) {
                            ID = "2000000001";
                        } else {
                            ID = String.valueOf((Integer.parseInt(ID) + 1));
                        }
                    }
                    System.out.println("自增ID：" + ID);

                    //获取cCodes值
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

                    System.out.println("获取的cCodes值：" + cCodes);

                    //查询供应商的联系人，从供应商档案里进行查询
                    String cVenPerson = this.jdbcTemplate.queryForObject(
                            "select cVenPerson from Vendor where cVenName = '" + baoJia.getcVenName() + "'", String.class);
                    System.out.println("供应商联系人：" + cVenPerson);

                    //查询供应商的电话，从供应商档案里进行查询
                    String cVenPhone = this.jdbcTemplate.queryForObject(
                            "select cVenPhone from Vendor where cVenName = '" + baoJia.getcVenName() + "'", String.class);
                    System.out.println("供应商联系人电话：" + cVenPhone);

                    //币种
//                    String cexch_name = this.jdbcTemplate.queryForObject(
//                            "select cexch_name from HY_DZ_HU_ConsultPrice where cCode='0000000001'", String.class);
//                    System.out.println("币种：" + cexch_name);

                    //供应商编码
                    String cVenCode = this.jdbcTemplate.queryForObject(
                            "select cVenCode from Vendor where cVenName = '" + baoJia.getcVenName() + "'", String.class);
                    System.out.println("供应商编码：" + cVenCode);

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    //行条码
                    String csysbarcode = "||puxj|" + cCodes;

                    //获取制单人
                    String cHandler = this.jdbcTemplate.queryForObject("select Person.cPersonCode from Person " +
                            "where Person.cPersonName='" +baoJia.getcPsn_Name() + "'", String.class);
//                    System.out.println(sql4);
                    System.out.println("主表7：" + cHandler);

                    //插入到供应商报价单中
                    String sql = "insert into HY_DZ_HU_ConsultPrice(ID, cCode, cVenCode, cVenPerson, cVenPhone, cVenPhone2, cVenFax, cVenHand, cexch_name, nflat, iTaxRate, cHandler," +
                            " dDate, cMaker, cDate, cVerifier, dVeriDate, cState, Remarks, cFlag, CreatePO, iswfcontrolled, ireturncount, iverifystate, cHandleDate, iverifystateex" +
                            ", Transfer, VerifyDate, cverfier, cdefine1, cdefine2, cdefine3, cdefine4, cdefine5, cdefine6, cdefine7, cdefine8, cdefine9, cdefine10, cdefine11" +
                            ", cdefine12, cdefine13, cdefine14, cdefine15, cdefine16, ivtid, iPrintCount, csysbarcode, cCurrentAuditor) VALUES (" + ID + ",'" + cCodes + "','"
                            + cVenCode + "','" + cVenPerson + "','" + cVenPhone + "','','','" + cVenPhone + "','人民币',1,16,'" + cHandler + "','" + date
                            + "','" + baoJia.getcPsn_Name() + "','" + date + "',NULL" + ",NULL,0,'',0,'',0,0,0,'',0,'',NULL,'','','','','',0,'',0,'','','','','','','',0,0,131057,0,'"
                            + csysbarcode + "',NULL)";
                    jdbcTemplate.update(sql);

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
                    System.out.println("获取AutoID的值：" + AutoID);

                    System.out.println("select ID from HY_DZ_HU_ConsultPrice where cVenCode='"
                            + baoJia.getcVenCode() + "' and dDate='" + date + "' and cMaker='" + baoJia.getcPsn_Name() + "'");

                    String ID2 = this.jdbcTemplate.queryForObject("select ID from HY_DZ_HU_ConsultPrice where cVenCode='"
                            + baoJia.getcVenCode() + "' and dDate='" + date + "' and cMaker='" + baoJia.getcPsn_Name() + "'", String.class);
                    System.out.println("子表主键ID :" + ID2);

                    String cCodeZ = this.jdbcTemplate.queryForObject("select cCode from HY_DZ_HU_ConsultPrice where cVenCode='"
                            + baoJia.getcVenCode() + "' and dDate='" + date + "' and cMaker='" + baoJia.getcPsn_Name() + "'", String.class);
                    System.out.println("单据编号：" + cCodeZ);

                    //税率，根据选取的物料进行不同的税率计算
                    String iTaxRate = this.jdbcTemplate.queryForObject(
                            "select iTaxRate from Inventory where cInvCode = '" + baoJia.getcInvCode() + "'", String.class);
                    System.out.println("税率：" + iTaxRate);

                    //行条码
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
                    System.out.println("行条码: " + cbsy);

                    //无含税单价的运算
                    String string = baoJia.getiInvSCost() + "-(" + baoJia.getiInvSCost() + "/(1+" + iTaxRate + "/100)*(" + iTaxRate + "/100))";

                    //四舍五入
                    DecimalFormat df = new DecimalFormat("#.000000");
                    Float iUnitPrice = Float.valueOf(df.format(jse.eval(string)));
                    System.out.println("无税单价：" + iUnitPrice);

                    //备注
                    String Remarks = "MuJuPrice:" + baoJia.getiModSCost();

                    if (baoJia.getcInvStd().equals("")) {
                        //关联单据
                        String IDPlanSQL = "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJia.getcCode() + "' " +
                                "and cInvName='" + baoJia.getcInvName() + "' and cInvCode='" + baoJia.getcInvCode() + "' " +
                                "and cInvstd=''";
                        String IDPlan = this.jdbcTemplate.queryForObject(IDPlanSQL, String.class);
                        System.out.println("关联单据：" + IDPlan);

                        String sql4 = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJia.getcCode()
                                + "'," + iTaxRate + ",'" + baoJia.getcInvCode() + "',1000024365," + baoJia.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJia.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJia.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql4);

                        String sql9 = "update supp_Quote set iInvSCost=" + baoJia.getiInvSCost() + ",iModSCost="
                                + baoJia.getiModSCost() + ",status='4' where cVenName='" + baoJia.getcVenName() + "' " +
                                "and cCode='" + baoJia.getcCode() + "' and cInvName='" + baoJia.getcInvName() + "' " +
                                "and cInvStd='' and historyStatus=0";
                        jdbcTemplate.update(sql9);
                    } else {
                        //关联单据
                        String IDPlanSQL = "select AutoID from HY_DZ_HU_AskPricePlans where cCode='" + baoJia.getcCode() + "' " +
                                "and cInvName='" + baoJia.getcInvName() + "' and cInvCode='" + baoJia.getcInvCode() + "' " +
                                "and cInvstd='" + baoJia.getcInvStd() + "'";
                        String IDPlan = this.jdbcTemplate.queryForObject(IDPlanSQL, String.class);
                        System.out.println("关联单据：" + IDPlan);

                        String sql4 = "insert into HY_DZ_HU_ConsultPrices(AutoID, ID, cCodeZ, iRowNo, IDPlan, cCodePlan, iTaxRate, cInvCode, partid, iQuantity, iLowerLimit, " +
                                "iUpperLimit, iUnitPrice, iUnitPriceTax, iOkPrice, OkPrice, Ddate, EffectDate, Remarks, fnum, cinva_unitcode, iinvexchrate, cdefine22, cdefine23," +
                                " cdefine24, cdefine25, cdefine26, cdefine27, cdefine28, cdefine29, cdefine30, cdefine31, cdefine32, cdefine33, cdefine34, cdefine35, cdefine36," +
                                " cdefine37, cbsysbarcode, cItem_class, cItemCode, cItemName) values (" + AutoID + "," + ID + ",'" + cCodeZ + "',1," + IDPlan + ",'" + baoJia.getcCode()
                                + "'," + iTaxRate + ",'" + baoJia.getcInvCode() + "',1000024365," + baoJia.getiQuantity() + ",0,0," + iUnitPrice + "," + baoJia.getiInvSCost()
                                + "," + iUnitPrice + "," + baoJia.getiInvSCost() + ",'" + date + "','2099-12-31','" + Remarks + "',NULL,'',NULL,'1','','','',0,0,'','','','','','',0" +
                                ",0,'1900-01-01','1900-01-01','" + cbsy + "','','','')";
                        jdbcTemplate.update(sql4);

                        String sql9 = "update supp_Quote set iInvSCost=" + baoJia.getiInvSCost() + ",iModSCost="
                                + baoJia.getiModSCost() + ",status='4' where cVenName='" + baoJia.getcVenName() + "' " +
                                "and cCode='" + baoJia.getcCode() + "' and cInvName='" + baoJia.getcInvName() + "' and cInvStd='"
                                + baoJia.getcInvStd() + "' and historyStatus=0";
                        jdbcTemplate.update(sql9);
                    }
                }

            }

            object.put("code", 0);
            object.put("msg", "审核成功");
            System.out.println(object.toJSONString());
            return object.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            object.put("code", 1);
            object.put("msg", "审核失败");
            System.out.println(object.toJSONString());
            return object.toJSONString();
        }
    }

}
