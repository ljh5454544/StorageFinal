package com.jia.storage.controller.commodity;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Commodity;
import com.jia.storage.service.CommoditySerivce;
import com.jia.storage.service.FileSerice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "商品文件接口", description = "上传下载相关")
@Controller
@RequestMapping("/comm")
public class CommFileController {

    @Autowired
    FileSerice fileSerice;
    @Autowired
    CommoditySerivce commoditySerivce;

    @ApiOperation(value = "商品上传", notes = "批量上传商品信息")
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String fileUpLoad(MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if (file.isEmpty()){
            map.put("judge", 0);
        }else{
            if (file.getSize() != 0 && file != null){
                List<Commodity> commodities = fileSerice.commFileRes(file);
                boolean b = commoditySerivce.addCommoditys(commodities);
                if (b){
                    map.put("judge", 1);
                }else{
                    map.put("judge", 0);
                }
            }
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "下载Excel文件模板", notes = "下载商品信息录入文件模板")
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public void downLoadFile(HttpServletResponse response){
        InputStream bis = null;
        BufferedOutputStream out = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("model/model-comm.xls");
            InputStream inputStream = classPathResource.getInputStream();
            //获取输入流
            bis = new BufferedInputStream(inputStream);
            //假如以中文名下载的话
            String filename = "model-comm.xls";
            //转码，免得文件名中文乱码
            filename = URLEncoder.encode(filename,"UTF-8");
            //设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            out = new BufferedOutputStream(response.getOutputStream());
            int len = 0;
            while((len = bis.read()) != -1){
                out.write(len);
                out.flush();
            }
            out.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
