package com.guodian.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.system.LogInClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChangePass {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/ShowAllThings")
    public String ShowAllThings() {
        JSONObject object = new JSONObject();
        String sql = "select cVenName,Pass from supp_Logon where Admin=3";
        List<LogInClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(LogInClass.class));
        System.out.println(list);
        object.put("code", 0);
        object.put("msg", "");
        object.put("data", list);
        return object.toJSONString();
    }
}
