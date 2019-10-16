package com.guodian.controller.bidding;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.supplierpage.BaoJiaClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 已过期列表
 */
@RestController
@Transactional
public class ExpiredController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询到期日小于当前日期的数据
     * @return
     */
    @RequestMapping("/expired")
    public String expired(String username,String Admin){
        System.out.println(username+","+Admin);
        JSONObject object = new JSONObject();
        String num = Admin.substring(1, Admin.length() - 1);
        if (num.equals("0")){
            String sql = "select * from supp_Quote where outDate<'"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"'";
            List<BaoJiaClass> list = jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<>(BaoJiaClass.class));
            object.put("code",0);
            object.put("msg","");
            object.put("data", list);
            return object.toJSONString();
        }else {
            String sql = "select * from supp_Quote where cPsn_Name="+username+
                    " and outDate<'"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"'";
            List<BaoJiaClass> list = jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<>(BaoJiaClass.class));
            object.put("code",0);
            object.put("msg","");
            object.put("data", list);
            return object.toJSONString();
        }

    }

}
