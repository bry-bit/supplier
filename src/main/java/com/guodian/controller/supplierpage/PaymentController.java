package com.guodian.controller.supplierpage;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.supplierpage.PaymentClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 挂账查询
 */
@RestController
@Transactional
public class PaymentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 挂账查询
     *
     * @return
     */
    @RequestMapping("/queryPayment")
    public String queryPayment(String username, String Admin, Integer page, Integer limit) {
        System.out.println("username：" + username + ",Admin：" + Admin);
        System.out.println("page:" + page + ",limit:" + limit);
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
        if (!num.equals("3")) {
            String sql = "SELECT TOP " + limit + " d.* FROM(SELECT c.*,row_number() over(ORDER BY cPBVCode ASC) as rownumber FROM(" +
                    "select PurBillVouchs.iOriSum,Inventory.cInvName,PurBillVouch.cPBVCode,PurBillVouchs.cInvCode" +
                    ",Inventory.cInvStd,PurBillVouchs.iPBVQuantity,PurBillVouchs.iOriCost,PurBillVouchs.iOriTaxCost" +
                    ",PurBillVouchs.iTaxRate,PurBillVouchs.iOriTaxPrice,PurBillVouch.dVouDate,PurBillVouch.dSDate" +
                    ",PurBillVouch.dverifydate,Person.cPersonName,Vendor.cVenName,PurBillVouchs.iOriMoney,PurBillVouch.cInCode" +
                    ",PO_Pomain.cPOID from PurBillVouchs inner join PurBillVouch on PurBillVouch.PBVID=PurBillVouchs.PBVID " +
                    "left join Inventory on Inventory.cInvCode=PurBillVouchs.cInvCode " +
                    "left join Person on PurBillVouch.cPersonCode = Person.cPersonCode " +
                    "left join Vendor on PurBillVouch.cVenCode = Vendor.cVenCode " +
                    "left join PO_Podetails on PO_Podetails.ID=PurBillVouchs.iPOsID " +
                    "left join PO_Pomain on PO_Podetails.POID=PO_Pomain.POID " +
                    "where PurBillVouch.dSDate is not NULL and PurBillVouch.cPBVVerifier is not null " +
                    "and PurBillVouch.dverifydate is not null ) as c) as d WHERE rownumber >" + ((limit * page) - limit);
//            System.out.println("SQL：" + sql);
            List<PaymentClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));
//            System.out.println(list);
            String SQL = "SELECT d.* FROM(SELECT c.*,row_number() over(ORDER BY cPBVCode ASC) as rownumber FROM(" +
                    "select PurBillVouchs.iOriSum,Inventory.cInvName,PurBillVouch.cPBVCode,PurBillVouchs.cInvCode" +
                    ",Inventory.cInvStd,PurBillVouchs.iPBVQuantity,PurBillVouchs.iOriCost,PurBillVouchs.iOriTaxCost" +
                    ",PurBillVouchs.iTaxRate,PurBillVouchs.iOriTaxPrice,PurBillVouch.dVouDate,PurBillVouch.dSDate" +
                    ",PurBillVouch.dverifydate,Person.cPersonName,Vendor.cVenName,PurBillVouchs.iOriMoney,PurBillVouch.cInCode" +
                    ",PO_Pomain.cPOID from PurBillVouchs inner join PurBillVouch on PurBillVouch.PBVID=PurBillVouchs.PBVID " +
                    "left join Inventory on Inventory.cInvCode=PurBillVouchs.cInvCode " +
                    "left join Person on PurBillVouch.cPersonCode = Person.cPersonCode " +
                    "left join Vendor on PurBillVouch.cVenCode = Vendor.cVenCode " +
                    "left join PO_Podetails on PO_Podetails.ID=PurBillVouchs.iPOsID " +
                    "left join PO_Pomain on PO_Podetails.POID=PO_Pomain.POID " +
                    "where PurBillVouch.dSDate is not NULL and PurBillVouch.cPBVVerifier is not null " +
                    "and PurBillVouch.dverifydate is not null ) as c) as d ";
            List<PaymentClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        } else {
            String sql = "SELECT TOP " + limit + " d.* FROM(SELECT c.*,row_number() over(ORDER BY cPBVCode ASC) as rownumber FROM(" +
                    "select PurBillVouchs.iOriSum,Inventory.cInvName,PurBillVouch.cPBVCode,PurBillVouchs.cInvCode" +
                    ",Inventory.cInvStd,PurBillVouchs.iPBVQuantity,PurBillVouchs.iOriCost,PurBillVouchs.iOriTaxCost" +
                    ",PurBillVouchs.iTaxRate,PurBillVouchs.iOriTaxPrice,PurBillVouch.dVouDate,PurBillVouch.dSDate" +
                    ",PurBillVouch.dverifydate,Person.cPersonName,Vendor.cVenName,PurBillVouchs.iOriMoney,PurBillVouch.cInCode" +
                    ",PO_Pomain.cPOID from PurBillVouchs inner join PurBillVouch on PurBillVouch.PBVID=PurBillVouchs.PBVID " +
                    "left join Inventory on Inventory.cInvCode=PurBillVouchs.cInvCode " +
                    "left join Person on PurBillVouch.cPersonCode = Person.cPersonCode " +
                    "left join Vendor on PurBillVouch.cVenCode = Vendor.cVenCode " +
                    "left join PO_Podetails on PO_Podetails.ID=PurBillVouchs.iPOsID " +
                    "left join PO_Pomain on PO_Podetails.POID=PO_Pomain.POID " +
                    "where Vendor.cVenName=" + username + " and PurBillVouch.dSDate is NULL and PurBillVouch.cPBVVerifier is not null " +
                    "and PurBillVouch.dverifydate is not null ) as c) as d WHERE rownumber>" + ((limit * page) - limit);

            List<PaymentClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));

            String SQL = "SELECT d.* FROM(SELECT c.*,row_number() over(ORDER BY cPBVCode ASC) as rownumber FROM(" +
                    "select PurBillVouchs.iOriSum,Inventory.cInvName,PurBillVouch.cPBVCode,PurBillVouchs.cInvCode" +
                    ",Inventory.cInvStd,PurBillVouchs.iPBVQuantity,PurBillVouchs.iOriCost,PurBillVouchs.iOriTaxCost" +
                    ",PurBillVouchs.iTaxRate,PurBillVouchs.iOriTaxPrice,PurBillVouch.dVouDate,PurBillVouch.dSDate" +
                    ",PurBillVouch.dverifydate,Person.cPersonName,Vendor.cVenName,PurBillVouchs.iOriMoney,PurBillVouch.cInCode" +
                    ",PO_Pomain.cPOID from PurBillVouchs inner join PurBillVouch on PurBillVouch.PBVID=PurBillVouchs.PBVID " +
                    "left join Inventory on Inventory.cInvCode=PurBillVouchs.cInvCode " +
                    "left join Person on PurBillVouch.cPersonCode = Person.cPersonCode " +
                    "left join Vendor on PurBillVouch.cVenCode = Vendor.cVenCode " +
                    "left join PO_Podetails on PO_Podetails.ID=PurBillVouchs.iPOsID " +
                    "left join PO_Pomain on PO_Podetails.POID=PO_Pomain.POID " +
                    "where Vendor.cVenName=" + username + " and PurBillVouch.dSDate is NULL and PurBillVouch.cPBVVerifier is not null " +
                    "and PurBillVouch.dverifydate is not null ) as c) as d";
            List<PaymentClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        }
    }

    /**
     * 通过多个时间段进行查询数据
     *
     * @param
     * @return
     */
    @RequestMapping("/findByDate")
    public String findByDate(PaymentClass paymentClass, String Admin, String username, Integer page, Integer limit) {
        String num = Admin.substring(1, Admin.length() - 1);
        JSONObject object = new JSONObject();
        String date = paymentClass.getdVouDate();
        String[] dates = date.split(" ");
        String sql = "SELECT TOP " + limit + " d.* FROM(SELECT c.*,row_number() over(ORDER BY cPBVCode ASC) as rownumber FROM(" +
                "select PurBillVouchs.iOriSum,Inventory.cInvName,PurBillVouch.cPBVCode,PurBillVouchs.cInvCode" +
                ",Inventory.cInvStd,PurBillVouchs.iPBVQuantity,PurBillVouchs.iOriCost,PurBillVouchs.iOriTaxCost" +
                ",PurBillVouchs.iTaxRate,PurBillVouchs.iOriTaxPrice,PurBillVouch.dVouDate,PurBillVouch.dSDate" +
                ",PurBillVouch.dverifydate,Person.cPersonName,Vendor.cVenName,PurBillVouchs.iOriMoney,PurBillVouch.cInCode" +
                ",PO_Pomain.cPOID from PurBillVouchs inner join PurBillVouch on PurBillVouch.PBVID=PurBillVouchs.PBVID " +
                "left join Inventory on Inventory.cInvCode=PurBillVouchs.cInvCode " +
                "left join Person on PurBillVouch.cPersonCode = Person.cPersonCode " +
                "left join Vendor on PurBillVouch.cVenCode = Vendor.cVenCode " +
                "left join PO_Podetails on PO_Podetails.ID=PurBillVouchs.iPOsID " +
                "left join PO_Pomain on PO_Podetails.POID=PO_Pomain.POID " +
                "where PurBillVouch.dSDate is not NULL and PurBillVouch.cPBVVerifier is not null " +
                "and PurBillVouch.dverifydate is not null";

        String SQL = "SELECT d.* FROM(SELECT c.*,row_number() over(ORDER BY cPBVCode ASC) as rownumber FROM(" +
                "select PurBillVouchs.iOriSum,Inventory.cInvName,PurBillVouch.cPBVCode,PurBillVouchs.cInvCode" +
                ",Inventory.cInvStd,PurBillVouchs.iPBVQuantity,PurBillVouchs.iOriCost,PurBillVouchs.iOriTaxCost" +
                ",PurBillVouchs.iTaxRate,PurBillVouchs.iOriTaxPrice,PurBillVouch.dVouDate,PurBillVouch.dSDate" +
                ",PurBillVouch.dverifydate,Person.cPersonName,Vendor.cVenName,PurBillVouchs.iOriMoney,PurBillVouch.cInCode" +
                ",PO_Pomain.cPOID from PurBillVouchs inner join PurBillVouch on PurBillVouch.PBVID=PurBillVouchs.PBVID " +
                "left join Inventory on Inventory.cInvCode=PurBillVouchs.cInvCode " +
                "left join Person on PurBillVouch.cPersonCode = Person.cPersonCode " +
                "left join Vendor on PurBillVouch.cVenCode = Vendor.cVenCode " +
                "left join PO_Podetails on PO_Podetails.ID=PurBillVouchs.iPOsID " +
                "left join PO_Pomain on PO_Podetails.POID=PO_Pomain.POID " +
                "where PurBillVouch.dSDate is not NULL and PurBillVouch.cPBVVerifier is not null " +
                "and PurBillVouch.dverifydate is not null";
        if (!num.equals("3")) {
            System.out.println("接受的值 :  " + paymentClass);
            if (paymentClass.getdVouDate() != null && paymentClass.getdVouDate().length() != 0) {
                System.out.println("发票日期 : " + paymentClass.getdVouDate());
                sql = sql + " and PurBillVouch.dVouDate >= '" + dates[0] + "' and PurBillVouch.dVouDate <='" + dates[2] + "'";
                SQL = SQL + " and PurBillVouch.dVouDate >= '" + dates[0] + "' and PurBillVouch.dVouDate <='" + dates[2] + "'";
            }
            if (paymentClass.getcPBVCode() != null && paymentClass.getcPBVCode().length() != 0) {
                System.out.println("发票编号   : " + paymentClass.getcPBVCode());
                sql = sql + " and PurBillVouch.cPBVCode like '%" + paymentClass.getcPBVCode() + "%'";
                SQL = SQL + " and PurBillVouch.cPBVCode like '%" + paymentClass.getcPBVCode() + "%'";
            }
            if (paymentClass.getcInvCode() != null && paymentClass.getcInvCode().length() != 0) {
                System.out.println("存货编码   : " + paymentClass.getcInvCode());
                sql = sql + " and PurBillVouchs.cInvCode like '%" + paymentClass.getcInvCode() + "%'";
                SQL = SQL + " and PurBillVouchs.cInvCode like '%" + paymentClass.getcInvCode() + "%'";
            }
            if (paymentClass.getcVenName() != null && paymentClass.getcVenName().length() != 0) {
                System.out.println("供应商名称   : " + paymentClass.getcVenName());
                sql = sql + " and Vendor.cVenName like '%" + paymentClass.getcVenName() + "%'";
                SQL = SQL + " and Vendor.cVenName like '%" + paymentClass.getcVenName() + "%'";
            }
            sql = sql + " ) as c) as d WHERE rownumber>" + ((limit * page) - limit);
            SQL = SQL + " ) as c) as d ";
            List<PaymentClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));
            List<PaymentClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        } else {
            if (paymentClass.getdVouDate() != null && paymentClass.getdVouDate().length() != 0) {
                System.out.println("发票日期 : " + paymentClass.getdVouDate());
                sql = sql + " and PurBillVouch.dVouDate >= '" + dates[0] + "' and PurBillVouch.dVouDate <='" + dates[2] + "'";
                SQL = SQL + " and PurBillVouch.dVouDate >= '" + dates[0] + "' and PurBillVouch.dVouDate <='" + dates[2] + "'";
            }
            if (paymentClass.getcPBVCode() != null && paymentClass.getcPBVCode().length() != 0) {
                System.out.println("发票编号   : " + paymentClass.getcPBVCode());
                sql = sql + " and PurBillVouch.cPBVCode like '%" + paymentClass.getcPBVCode() + "%'";
                SQL = SQL + " and PurBillVouch.cPBVCode like '%" + paymentClass.getcPBVCode() + "%'";
            }
            if (paymentClass.getcInvCode() != null && paymentClass.getcInvCode().length() != 0) {
                System.out.println("存货编码   : " + paymentClass.getcInvCode());
                sql = sql + " and PurBillVouchs.cInvCode like '%" + paymentClass.getcInvCode() + "%'";
                SQL = SQL + " and PurBillVouchs.cInvCode like '%" + paymentClass.getcInvCode() + "%'";
            }

            sql = sql + " and Vendor.cVenName=" + username + ") as c) as d WHERE rownumber>" + ((limit * page) - limit);
            SQL = SQL + " and Vendor.cVenName=" + username + ") as c) as d ";
            List<PaymentClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));
            List<PaymentClass> lists = jdbcTemplate.query(SQL, new Object[]{}, new BeanPropertyRowMapper<>(PaymentClass.class));

            object.put("code", 0);
            object.put("msg", "");
            object.put("count", lists.size());
            object.put("data", list);
            return object.toJSONString();
        }

    }


}
