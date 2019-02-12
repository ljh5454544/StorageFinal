package com.jia.storage.controller.tenant;


import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Tenant;
import com.jia.storage.service.TenantService;
import com.jia.storage.util.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "商户信息管理", description = "商户信息管理接口")
@Controller
@RequestMapping("/tenant/")
public class TenantController {

    @Autowired
    TenantService tenantService;

    @ApiOperation(value = "批量删除商户信息", notes = "删除指定List的商户信息")
    @ResponseBody
    @RequestMapping(value = "deletes", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteAll(@RequestBody String json){
        Map<String, Integer> map = new HashMap();
        List<Long> array = JSON.parseArray(json, Long.class);
        boolean result = tenantService.removeCommdity(array);
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "更新用户", notes = "更新指定ID的用户信息")
    @ResponseBody
    @RequestMapping(value = "update", method = {RequestMethod.POST}, consumes = "application/json")
    public String updateCommodity(@RequestBody String json){
        Tenant commodity = JSON.parseObject(json, Tenant.class);

        System.out.println(commodity);
        Map<String, Integer> map = new HashMap();
        boolean result = tenantService.updateCommdity(commodity);
        if ( result ){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "商户信息", notes = "获取指定ID的商户的信息")
    @ResponseBody
    @RequestMapping(value = "msg", method = {RequestMethod.POST}, consumes = "application/json")
    public String getComm(@RequestBody String json){
        Tenant comm = JSON.parseObject(json, Tenant.class);
        Map<String, Object> map = new HashMap();
        Tenant tenant = tenantService.getCommdity(comm);
        if ( tenant != null || tenant.getId() != null){
            map.put("judge", 1);
            map.put("tenant", tenant);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "删除商户", notes = "删除指定ID的商户")
    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteComm(@RequestBody String json){
        Tenant comm = JSON.parseObject(json, Tenant.class);
        Map<String, Object> map = new HashMap();
        boolean b = tenantService.removeCommdity(comm);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "添加商户", notes = "添加商户")
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST}, consumes = "application/json")
    public String addComm(@RequestBody String json){
        Tenant comm = JSON.parseObject(json, Tenant.class);
        Map<String, Object> map = new HashMap();
        boolean b = tenantService.addCommodity(comm);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "用户分页信息", notes = "获取指定页码的用户信息")
    @ResponseBody
    @RequestMapping(value = "page/{pagenum}", method = {RequestMethod.GET})
    public String getPage(@PathVariable("pagenum") Integer pagenum){
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pagenum);
        List<Tenant> comm = tenantService.getByPage(pageBean);
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", comm);
        map.put("page", pageBean);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取全部商户", notes = "全部商户")
    @ResponseBody
    @RequestMapping(value = "all", method = {RequestMethod.GET})
    public String getAllTenant(){
        List<Tenant> allTenant = tenantService.getAllTenant();
        return JSON.toJSONString(allTenant);
    }


}
