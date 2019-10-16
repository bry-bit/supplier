package com.guodian.controller.supplierpage;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.supplierpage.BomClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 合同执行
 */
@RestController
@Transactional
public class MaterialController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询数据库并展示在页面上
     *
     * @return
     */
    @RequestMapping("/getBom")
    public String getBom(String username, String Admin, Integer page, Integer limit) {
        System.out.println("username：" + username + ",Admin：" + Admin);
        System.out.println("page：" + page + "，limit：" + limit);
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
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null ) as c" +
                    ") as d WHERE rownumber> " + ((limit * page) - limit);
            System.out.println(sql);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));
            System.out.println(list);

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
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null ) as c" +
                    ") as d";

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
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null ) as c" +
                    ") as d " +
                    "WHERE rownumber> " + ((limit * page) - limit);
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
                    "where Vendor.cVenName=" + username + " and PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null ) as c" +
                    ") as d ";
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        }
    }

    /**
     * 根据不同条件进行查询
     *
     * @param bomClass
     * @param Admin
     * @return
     */
    @RequestMapping("/SearchMaterial")
    public String SearchMaterial(BomClass bomClass, String Admin, Integer page, Integer limit) {
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
        //根据时间
        String date = bomClass.getdPODate();
        String[] dates = date.split(" ");
        if (!num.equals("3")) {
            //搜索某一条件的数据
            String sql = "SELECT TOP " + limit + " d.* FROM (SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null";

            //某一条件吗所查出的数量
            String SQL = "SELECT d.* FROM (SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null";
            if (bomClass.getcPOID() != null && bomClass.getcPOID().length() != 0) {
                sql = sql + " and PO_Pomain.cPOID like '%" + bomClass.getcPOID() + "%'";
                SQL = SQL + " and PO_Pomain.cPOID like '%" + bomClass.getcPOID() + "%'";
            }

            if (bomClass.getdPODate() != null && bomClass.getdPODate().length() != 0) {
                sql = sql + " and  (PO_Pomain.dPODate >='" + dates[0] + "' and  PO_Pomain.dPODate<='" + dates[2] + "')";
                SQL = SQL + " and  (PO_Pomain.dPODate >='" + dates[0] + "' and  PO_Pomain.dPODate<='" + dates[2] + "')";
            }

            if (bomClass.getcVenName() != null && bomClass.getcVenName().length() != 0) {
                sql = sql + " and Vendor.cVenName like '%" + bomClass.getcVenName() + "%'";
                SQL = SQL + " and Vendor.cVenName like '%" + bomClass.getcVenName() + "%'";
            }
            if (bomClass.getcInvCode() != null && bomClass.getcInvCode().length() != 0) {
                sql = sql + " and PO_Podetails.cInvCode like '%" + bomClass.getcInvCode() + "%'";
                SQL = SQL + " and PO_Podetails.cInvCode like '%" + bomClass.getcInvCode() + "%'";
            }

            sql = sql + ") as c) as d WHERE rownumber>" + ((limit * page) - limit);
            System.out.println("查询的sql：" + sql);

            SQL = SQL + ") as c) as d ";
            System.out.println("..:" + SQL);

            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        } else {
            String sql = "SELECT TOP " + limit + " d.* FROM (SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null";

            String SQL = "SELECT d.* FROM (SELECT c.*,ROW_NUMBER() over(ORDER BY cPOID ASC) as rownumber FROM" +
                    "(select PO_Pomain.cPOID,PO_Pomain.dPODate,PO_Podetails.iQuantity,PO_Podetails.cInvCode,Inventory.cInvName" +
                    ",Inventory.cInvStd,PO_Podetails.dArriveDate,PO_Podetails.iPerTaxRate,PO_Podetails.iUnitPrice,PO_Podetails.iMoney" +
                    ",PO_Podetails.iInvQTY,Vendor.cVenName,PO_Podetails.iTaxPrice,PO_Podetails.iSum,Person.cPersonName" +
                    ",PO_Podetails.iQuantity-ISNULL(PO_Podetails.fPoArrQuantity,0)+ISNULL(PO_Podetails.fPoRefuseQuantity,0)+ISNULL(PO_Podetails.fPoRetQuantity,0) as noArrQuantity " +
                    "from PO_Podetails " +
                    "inner join PO_Pomain on PO_Pomain.POID=PO_Podetails.POID " +
                    "left join Inventory on PO_Podetails.cInvCode=Inventory.cInvCode " +
                    "left join Vendor on PO_Pomain.cVenCode=Vendor.cVenCode " +
                    "left join Person on PO_Pomain.cPersonCode=Person.cPersonCode " +
                    "where PO_Pomain.cAuditDate is not null and PO_Podetails.cbCloseTime is null";
            if (bomClass.getcPOID() != null && bomClass.getcPOID().length() != 0) {
                sql = sql + " and PO_Pomain.cPOID like '%" + bomClass.getcPOID() + "%'";
                SQL = SQL + " and PO_Pomain.cPOID like '%" + bomClass.getcPOID() + "%'";
            }
            if (bomClass.getdPODate() != null && bomClass.getdPODate().length() != 0) {
                sql = sql + " and  PO_Pomain.dPODate >='" + dates[0] + "' and  PO_Pomain.dPODate<='" + dates[2] + "'";
                SQL = SQL + " and  PO_Pomain.dPODate >='" + dates[0] + "' and  PO_Pomain.dPODate<='" + dates[2] + "'";
            }

            if (bomClass.getcInvCode() != null && bomClass.getcInvCode().length() != 0) {
                sql = sql + " and PO_Podetails.cInvCode like '%" + bomClass.getcInvCode() + "%'";
                SQL = SQL + " and PO_Podetails.cInvCode like '%" + bomClass.getcInvCode() + "%'";
            }

            sql = sql + " and Vendor.cVenName='" + bomClass.getUname() + "') as c) as d WHERE rownumber>" + ((limit * page) - limit);
            SQL = SQL + " and Vendor.cVenName='" + bomClass.getUname() + "') as c) as d ";
            System.out.println("全部搜索的sql：" + sql);
            List<BomClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));
            List<BomClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(BomClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        }
    }

}
