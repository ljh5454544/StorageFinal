package com.jia.storage.controller.commodity;


import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Commodity;
import com.jia.storage.service.CommoditySerivce;
import com.jia.storage.util.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "商品接口", description = "和商品相关的所有操作")
@Controller
@RequestMapping("/comm/")
public class CommodityController {

    @Autowired
    CommoditySerivce commoditySerivce;

    @ApiOperation(value = "批量删除商品", notes = "通过List批量删除指定的商品")
    @ResponseBody
    @RequestMapping(value = "deletes", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteAll(@RequestBody String json){
        List<Long> array = JSON.parseArray(json, Long.class);
        boolean result = commoditySerivce.removeCommdity(array);
        Map<String, Integer> map = new HashMap();
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "更新商品", notes = "通过指定的ID更新商品")
    @ResponseBody
    @RequestMapping(value = "update", method = {RequestMethod.POST}, consumes = "application/json")
    public String updateCommodity(@RequestBody String json){
        Commodity commodity = JSON.parseObject(json, Commodity.class);
        Map<String, Integer> map = new HashMap();
        boolean result = commoditySerivce.updateCommdity(commodity);
        if ( result ){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取商品信息", notes = "通过指定的ID获取商品的信息")
    @ResponseBody
    @RequestMapping(value = "msg", method = {RequestMethod.POST}, consumes = "application/json")
    public String getComm(@RequestBody String json){
        Commodity comm = JSON.parseObject(json, Commodity.class);
        Map<String, Object> map = new HashMap();
        Commodity commdity = commoditySerivce.getCommdity(comm);
        if ( commdity != null || commdity.getId() != null){
            map.put("judge", 1);
            map.put("commdity", commdity);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "删除商品", notes = "通过指定的ID删除商品的信息")
    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteComm(@RequestBody String json){
        Commodity comm = JSON.parseObject(json, Commodity.class);
        Map<String, Object> map = new HashMap();
        boolean b = commoditySerivce.removeCommdity(comm);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "添加商品", notes = "添加商品信息")
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST}, consumes = "application/json")
    public String addComm(@RequestBody String json){
        Commodity comm = JSON.parseObject(json, Commodity.class);
        Map<String, Object> map = new HashMap();
        boolean b = commoditySerivce.addCommodity(comm);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "商品信息分页", notes = "获取指定页码的商品信息")
    @ResponseBody
    @RequestMapping("page/{pagenum}")
    public String getPage(@PathVariable("pagenum") Integer pagenum){
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pagenum);
        List<Commodity> comm = commoditySerivce.getByPage(pageBean);
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", comm);
        map.put("page", pageBean);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取所有商品信息", notes = "获取全部的商品信息")
    @ResponseBody
    @RequestMapping(value = "all", method = {RequestMethod.POST, RequestMethod.GET})
    public String getAll(){
        List<Commodity> list = commoditySerivce.getALL();
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", list);
        return JSON.toJSONString(map);
    }

}
