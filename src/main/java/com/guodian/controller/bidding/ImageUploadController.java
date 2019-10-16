package com.guodian.controller.bidding;

import com.alibaba.fastjson.JSONObject;
import com.guodian.domin.supplierpage.BaoJiaClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * 图片和文件上传接口
 */
@RestController
@Transactional
public class ImageUploadController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据编号查询所有数据
     */
    @RequestMapping("selectDownload")
    public String selectDownload(String ReqID) {
//        System.out.println("ReqID  : " + ReqID);
        String selectList = "select * from supp_Quote where ReqID = '" + ReqID + "'";
        List<BaoJiaClass> list = jdbcTemplate.query(selectList, new Object[]{}, new BeanPropertyRowMapper<>(BaoJiaClass.class));
        for (BaoJiaClass baoJiaClass : list) {
//            System.out.println(baoJiaClass);
        }

        JSONObject object = new JSONObject();
        object.put("code", 0);
        object.put("msg", "");
        object.put("list", list);
        return object.toJSONString();
    }

    /**
     * 当编辑时，展现图片和文件地址
     * @param ReqID
     * @return
     */
    @RequestMapping("SelectDatas")
    public String SelectDatas(String ReqID) {
        JSONObject object = new JSONObject();
        String sql = "select souURL from supp_ReqINVs where ReqID='" + ReqID + "'";
        String souURL = jdbcTemplate.queryForObject(sql, String.class);
        String sql2 = "select imgURL from supp_ReqINVs where ReqID='" + ReqID + "'";
        String imgURL = jdbcTemplate.queryForObject(sql2,String.class);
        String sql3 = "select mould from supp_ReqINVs where ReqID='"+ReqID+"'";
        String mould = jdbcTemplate.queryForObject(sql3,String.class);
        object.put("souURL", souURL);
        object.put("imgURL",imgURL);
        object.put("mould",mould);
        return object.toJSONString();
    }

    /**
     * 上传文件并保存
     *
     * @param file
     * @return
     */
    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        JSONObject object = new JSONObject();
        if (file.isEmpty()) {
//            System.out.println("文件为空！");
        }
        //文件名
        String fileName = file.getOriginalFilename();
//        System.out.println("fileName：" + fileName);

        //后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        System.out.println("suffixName：" + suffixName);

        //文件前缀名
        String Name = fileName.substring(0, fileName.indexOf("."));
//        System.out.println("Name：" + Name);
        String FileName = Name + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        System.out.println("FileName：" + FileName);

        //上传文件路径
        String filePath = "D://guodianGYSPT//apache-tomcat-9.0.20//webapps//supplier1//WEB-INF//classes//templates//views//upload//";
//        String filePath = "E://ideaIU-2019.2.win//workspace//supplier//src//main//resources//templates//views//upload//";
        //全路径读取
        File dest = new File(filePath + FileName + suffixName);
//        System.out.println("dest：" + dest);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        object.put("code", 0);
        object.put("msg", "");
        object.put("src", "../upload/" + FileName + suffixName);
        object.put("url", "../upload/" + FileName + suffixName);
//        System.out.println(object.toJSONString());
        return object.toJSONString();
    }

    /**
     * 附件下载
     *
     * @param str
     * @return
     */
    @RequestMapping("/downLoad")
    public String downLoad(@RequestBody String str) {
//        System.out.println("str:" + str);
        JSONObject object1 = new JSONObject();
        try {
            //将获取到的集合赋值在对象里
            net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(str);
            //   System.out.println("object:" + object);
            //获取data里的数据
            String msg = object.getString("data");
            //    System.out.println("msg:" + msg);

            //将json字符串赋值在对象里
            BaoJiaClass baoJiaClass = JSONObject.parseObject(msg, BaoJiaClass.class);
//            System.out.println("baoJiaClass:" + baoJiaClass);

            //根据相应的数据查询对应的文件信息
            String sql = "select souURL from supp_ReqINVs where ReqID='" + baoJiaClass.getReqID() + "'";
//            System.out.println("sql：" + sql);
            String souURL = this.jdbcTemplate.queryForObject(sql, String.class);
//               System.out.println("souURL:" + souURL);

            //获取文件名
            String[] fileName = souURL.split("/");

            object1.put("code", 200);
            object1.put("msg", fileName[2]);
            object1.put("url", "http://60.29.154.146:8277/views/upload/" + fileName[2]);
//            System.out.println("object1:" + object1.toJSONString());

            //如果能执行到这里说明下载成功,则根据 ReqID 和 cVenName 修改 supp_Quote 表的 Download 值变为1
            String updateSuppQuote = "update supp_Quote set Download = '1' where ReqID = ? and cVenName = ?";
            jdbcTemplate.update(updateSuppQuote, baoJiaClass.getReqID(), baoJiaClass.getcVenName());
            return object1.toJSONString();
        } catch (NullPointerException e) {
            object1.put("code", 202);
            object1.put("msg", e);
            return object1.toJSONString();
        }
    }
}
