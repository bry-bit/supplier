package com.guodian.controller.system;

import java.util.List;

import com.guodian.domin.system.LogInClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.system.Login;
import com.guodian.domin.system.UpdatePass;
import com.guodian.util.ObjectMapperUtil;

/**
 * 注册登录
 */
@RestController
public class LogInController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //    private Login testogin = new Login(1);
    private Login testogin = new Login(0);

    /**
     * 修改密码
     */
    @RequestMapping("updatePass")
    @ResponseBody
    public String updatePass(@RequestBody String s) {
        JSONObject object = new JSONObject();
        System.out.println("执行");
        System.out.println("输入 : " + s);
        UpdatePass updatePass = ObjectMapperUtil.toObject(s, UpdatePass.class);
        System.out.println("转化 : " + updatePass);
        //先根据Uname 查询 密码 和oldPass对比 不一样返回旧密码错误
        String selectSql = "select Pass from supp_Logon where Uname = '" + updatePass.getUname() + "'";
        List<LogInClass> list = jdbcTemplate.query(selectSql, new Object[]{}, new BeanPropertyRowMapper<>(LogInClass.class));
        if (list.get(0).getPass().equals(updatePass.getOldPass())) {
            //等于则修改数据
            String updateSql = "update supp_Logon set Pass = ? where Uname = '" + updatePass.getUname() + "'";
            jdbcTemplate.update(updateSql, updatePass.getNewPass());
            object.put("code", 0);
            object.put("msg", "修改成功,请重新登录");
            return object.toJSONString();
        } else {
            object.put("code", 0);
            object.put("msg", "旧密码与原密码不相等");
            return object.toJSONString();
        }
    }

    /**
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/testpost")
    @ResponseBody
    public String test(@RequestParam(value = "username", required = false) String username,
                       @RequestParam(value = "password", required = false) String password) {
        JSONObject object = new JSONObject();
        try {
            if (username == null || password == null) {
                object.put("code", 202);
                object.put("msg", "账号密码不能为空!!!");
                System.out.println(object.toJSONString());
                return object.toJSONString();
            }
            //根据登录名密码 查询 数据库是否存在
            //如果有 将权限返回来
            String sqlAdmin = "select Admin,Cphone,Pass from supp_Logon where Cphone = '" + username +
                    "' and Pass = '" + password + "'";
            List<Login> logInClass = jdbcTemplate.query(sqlAdmin, new Object[]{}, new BeanPropertyRowMapper<>(Login.class));
            //存储数据
            logInClass.get(0).setPass(password);
            logInClass.get(0).setUname(username);
            //如果是空说明 数据库没有  则返回错误
            System.out.println("查询出来的权限 : " + logInClass.get(0));
            if (logInClass.size() < 1) {
                object.put("code", 202);
                object.put("msg", "账号或密码错误!!!");
                System.out.println(object.toJSONString());
                return object.toJSONString();
            }
            //执行到这说明账号密码正确 将数据存到threadLocal中
            testogin = logInClass.get(0);

            String sql = "select * from supp_Logon where Cphone = '" + username + "' and Pass='" + password + "'";
            List<LogInClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(LogInClass.class));
            //最后返回登录成功
            object.put("code", 0);
            object.put("msg", "登录成功");
            object.put("data", list);
            System.out.println(object.toJSONString());
            return object.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            object.put("code", 404);
            object.put("msg", e.toString());
            return object.toJSONString();
        }
    }


    /**
     * @return
     */
    @RequestMapping("test")
    @ResponseBody
    public String test1() {
        System.out.println("测试成员变量 : " + testogin);
        if (testogin.getAdmin() == null) {
            JSONObject object1 = new JSONObject();
            object1.put("code", 200);
            object1.put("msg", "没有登录请登录");
            System.out.println(object1.toJSONString());
            return object1.toJSONString();
        }
        return testogin.getAdmin().toString();
    }

    /**
     * 注册信息入数据库
     *
     * @param logInClass
     * @return
     */
    @RequestMapping("/getUser")
    public String getUser(@RequestBody LogInClass logInClass) {
        JSONObject object = new JSONObject();
        try {
            String sql = "select * from supp_Logon where Uname='" + logInClass.getcVenName() + "'";
            List<LogInClass> list = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(LogInClass.class));
            if (list.size() == 0) {
                //查询供应商档案表的供应商编号
                String selectSql = "select cVenCode from Vendor where cVenName=" + "'" + logInClass.getcVenName() + "'";
                //进行赋值
                String cVenCode = this.jdbcTemplate.queryForObject(selectSql, String.class);

                //将注册信息传入数据库表中
                String sql1 = "insert into supp_Logon(cVenCode, cVenName, salesMan, Uname, Cphone, Email, Memo, Admin, Pass) values ('" +
                        cVenCode + "','" + logInClass.getcVenName() + "','','" + logInClass.getcVenName() + "','" + logInClass.getCphone()
                        + "','','',3,'" + logInClass.getPass() + "')";
                jdbcTemplate.update(sql1);

                object.put("code", 200);
                object.put("msg", "注册成功");
                return object.toJSONString();
            } else {
                object.put("code", 202);
                object.put("msg", "该用户已存在");
                return object.toJSONString();
            }
        } catch (Exception e) {
            object.put("code", 404);
            object.put("msg", "注册失败");
            return object.toJSONString();
        }
    }
}
