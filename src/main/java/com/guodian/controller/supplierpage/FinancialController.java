package com.guodian.controller.supplierpage;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.supplierpage.SearchDateClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.LayerUI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 财务对账
 */
@RestController
@Transactional
public class FinancialController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询采购入库单，并获取相对应字段的数据
     *
     * @return
     */
    @RequestMapping("/getMoney")
    public String getMoney(String username, String Admin, Integer page, Integer limit) {
        String num = Admin.substring(1, Admin.length() - 1);
        JSONObject object = new JSONObject();
        //获取近三个月的日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -90);
        Date date = calendar.getTime();
        String formDate = simpleDateFormat.format(date);
        if (!num.equals("3")) {
            String sql = "SELECT TOP " + limit + " d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "' ) as c) as d WHERE rownumber>" + ((limit * page) - limit);
            System.out.println(sql);
            List<SearchDateClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));
            String SQL = "SELECT d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "' ) as c) as d ";
            System.out.println(sql);
            List<SearchDateClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            System.out.println(list);
            return object.toJSONString();
        } else {
            String sql = "SELECT TOP " + limit + " d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "' and Vendor.cVenName=" + username + " ) as c) as d WHERE rownumber>"
                    + ((limit * page) - limit);
            System.out.println(sql);

            List<SearchDateClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));

            String SQL = "SELECT d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "' and Vendor.cVenName=" + username + " ) as c) as d";

            List<SearchDateClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            System.out.println(list);
            return object.toJSONString();
        }
    }

    /**
     * 通过多个时间段进行查询数据
     *
     * @param
     * @return
     */
    @RequestMapping("/searchDate")
    public String searchDate(SearchDateClass searchDateClass, Integer page, Integer limit) {
//        System.out.println(searchDateClass);
        JSONObject object = new JSONObject();
        String date = searchDateClass.getdDate();
        String[] dates = date.split(" ");
        //获取近三个月的日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -90);
        Date dateing = calendar.getTime();
        String formDate = simpleDateFormat.format(dateing);
        if (searchDateClass.getAdmin() != 3) {
            String sql = "SELECT TOP " + limit + " d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "'";

            String SQL = "SELECT d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "'";
            if (searchDateClass.getcVenName() != null && searchDateClass.getcVenName().length() != 0) {
                System.out.println(".....1");
                sql = sql + " and Vendor.cVenName like '%" + searchDateClass.getcVenName() + "%'";
                SQL = SQL + " and Vendor.cVenName like '%" + searchDateClass.getcVenName() + "%'";
                System.out.println(sql);
            }
            if (searchDateClass.getcCode() != null && searchDateClass.getcCode().length() != 0) {
                System.out.println(".....2");
                sql = sql + " and RdRecord01.cCode like '%" + searchDateClass.getcCode() + "%'";
                SQL = SQL + " and RdRecord01.cCode like '%" + searchDateClass.getcCode() + "%'";
                System.out.println(sql);
            }
            if (searchDateClass.getcInvCode() != null && searchDateClass.getcInvCode().length() != 0) {
                System.out.println(".....3");
                sql = sql + " and rdrecords01.cInvCode like '%" + searchDateClass.getcInvCode() + "%'";
                SQL = SQL + " and rdrecords01.cInvCode like '%" + searchDateClass.getcInvCode() + "%'";
                System.out.println(sql);
            }
            if (searchDateClass.getdDate() != null && searchDateClass.getdDate().length() != 0) {
                System.out.println(".....4");
                sql = sql + " and (RdRecord01.dDate>='" + dates[0] + "' and RdRecord01.dDate<='" + dates[2] + "')";
                SQL = SQL + " and (RdRecord01.dDate>='" + dates[0] + "' and RdRecord01.dDate<='" + dates[2] + "')";
                System.out.println(sql);
            }

            sql = sql + " ) as c) as d WHERE rownumber>" + ((limit * page) - limit);
            SQL = SQL + " ) as c) as d";
            List<SearchDateClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));
            System.out.println("list：" + list);

            List<SearchDateClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            System.out.println(object.toJSONString());
            return object.toJSONString();
        } else {
            String sql = "SELECT TOP " + limit + " d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "'";


            String SQL = "SELECT d.* FROM(SELECT c.*,row_number() over(ORDER BY cCode ASC) as rownumber FROM(" +
                    "select RdRecord01.dDate,RdRecord01.cCode,rdrecords01.cPOID,rdrecords01.iOriMoney,rdrecords01.iQuantity" +
                    ",rdrecords01.iOriCost,rdrecords01.iTaxRate,rdrecords01.iSumBillQuantity,rdrecords01.iOriTaxCost" +
                    ",Inventory.cInvCode,Inventory.cInvName,Inventory.cInvStd,Vendor.cVenName,Person.cPersonName" +
                    ",rdrecords01.iQuantity*rdrecords01.iOriTaxCost as iOriPrice from rdrecords01 " +
                    "inner join RdRecord01 on rdrecords01.ID=RdRecord01.ID " +
                    "left join Inventory on Inventory.cInvCode=rdrecords01.cInvCode " +
                    "left join Vendor on RdRecord01.cVenCode = Vendor.cVenCode " +
                    "left join Person on RdRecord01.cPersonCode = Person.cPersonCode " +
                    "where (RdRecord01.cHandler != '' or RdRecord01.cHandler is not null) " +
                    "and RdRecord01.dVeriDate is not null and RdRecord01.dDate<='" + simpleDateFormat.format(new Date()) + "' " +
                    "and RdRecord01.dDate>='" + formDate + "'";
            if (searchDateClass.getcVenName() != null && searchDateClass.getcVenName().length() != 0) {
                System.out.println(".....1");
                sql = sql + " and Vendor.cVenName like '%" + searchDateClass.getcVenName() + "%'";
                SQL = SQL + " and Vendor.cVenName like '%" + searchDateClass.getcVenName() + "%'";
                System.out.println(sql);
            }
            if (searchDateClass.getcCode() != null && searchDateClass.getcCode().length() != 0) {
                System.out.println(".....2");
                sql = sql + " and RdRecord01.cCode like '%" + searchDateClass.getcCode() + "%'";
                SQL = SQL + " and RdRecord01.cCode like '%" + searchDateClass.getcCode() + "%'";
                System.out.println(sql);
            }
            if (searchDateClass.getcInvCode() != null && searchDateClass.getcInvCode().length() != 0) {
                System.out.println(".....3");
                sql = sql + " and rdrecords01.cInvCode like '%" + searchDateClass.getcInvCode() + "%'";
                SQL = SQL + " and rdrecords01.cInvCode like '%" + searchDateClass.getcInvCode() + "%'";
                System.out.println(sql);
            }
            if (searchDateClass.getdDate() != null && searchDateClass.getdDate().length() != 0) {
                System.out.println(".....4");
                sql = sql + " and (RdRecord01.dDate>='" + dates[0] + "' and RdRecord01.dDate<='" + dates[2] + "')";
                SQL = SQL + " and (RdRecord01.dDate>='" + dates[0] + "' and RdRecord01.dDate<='" + dates[2] + "')";
                System.out.println(sql);
            }

            sql = sql + " and Vendor.cVenName = '" + searchDateClass.getUname() + "' ) as c) as d WHERE rownumber>" + ((limit * page) - limit);
            SQL = SQL + " and Vendor.cVenName = '" + searchDateClass.getUname() + "' ) as c) as d";
            System.out.println("供应商的：" + sql);
            List<SearchDateClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));
            System.out.println("list：" + list);
            List<SearchDateClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(SearchDateClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            System.out.println(object.toJSONString());
            return object.toJSONString();

        }


    }


}
