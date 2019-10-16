package com.guodian.controller.supplierpage;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.supplierpage.BomClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class YuJingDataController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 5-10日预警整体数据
     *
     * @param username
     * @param Admin
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/Warning")
    public String Warning(String username, String Admin, Integer page, Integer limit) {
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);

        if (!num.equals("3")) {
            String sql = "SELECT TOP " + limit + " d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) > GETDATE() " +
                    "and DATEADD(day,-10,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate>GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d WHERE rownumber> " + ((limit * page) - limit);
            System.out.println(sql);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            String SQL = "SELECT d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) > GETDATE() " +
                    "and DATEADD(day,-10,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate>GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d";
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        } else {
            String sql = "SELECT TOP " + limit + " d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) > GETDATE() " +
                    "and DATEADD(day,-10,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate>GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d WHERE rownumber> " + ((limit * page) - limit);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            String SQL = "SELECT d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) > GETDATE() " +
                    "and DATEADD(day,-10,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate>GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d";
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        }

    }

    /**
     * 5日内临期整体数据
     *
     * @param username
     * @param Admin
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/InThePeriod")
    public String InThePeriod(String username, String Admin, Integer page, Integer limit) {
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);

        if (!num.equals("3")) {
            String sql = "SELECT TOP " + limit + " d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate > GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d WHERE rownumber> " + ((limit * page) - limit);
            System.out.println(sql);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            String SQL = "SELECT d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate > GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d";
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        } else {
            String sql = "SELECT TOP " + limit + " d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate > GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d WHERE rownumber> " + ((limit * page) - limit);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            String SQL = "SELECT d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and DATEADD(day,-5,PO_Podetails.dArriveDate) < GETDATE() and PO_Podetails.dArriveDate > GETDATE()" +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d";
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        }

    }

    /**
     * 超期整体数据
     *
     * @param username
     * @param Admin
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/Beyond")
    public String Beyond(String username, String Admin, Integer page, Integer limit) {
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (!num.equals("3")) {
            String sql = "SELECT TOP " + limit + " d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and PO_Podetails.dArriveDate<'" + simpleDateFormat.format(new Date()) + "' " +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d WHERE rownumber> " + ((limit * page) - limit);
            System.out.println(sql);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            String SQL = "SELECT d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and PO_Podetails.dArriveDate<'" + simpleDateFormat.format(new Date()) + "' " +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d";
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        } else {
            String sql = "SELECT TOP " + limit + " d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and PO_Podetails.dArriveDate<'" + simpleDateFormat.format(new Date()) + "' " +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d WHERE rownumber> " + ((limit * page) - limit);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            String SQL = "SELECT d.* FROM" +
                    "(SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null " +
                    "and PO_Podetails.dArriveDate<'" + simpleDateFormat.format(new Date()) + "' " +
                    "and PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) !=0 " +
                    ") as c) as d";
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));
            object.put("code", 0);
            object.put("msg", "");
            object.put("count",lists.size());
            object.put("data", list);
            return object.toJSONString();
        }

    }
}
