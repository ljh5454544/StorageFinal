package com.jia.storage.controller.inventory;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Inventory;
import com.jia.storage.service.InventoryService;
import com.jia.storage.service.RecordService;
import com.jia.storage.util.PageBean;
import com.jia.storage.util.UUIDUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "仓储存储信息接口", description = "管理仓储的入库出库信息")
@Controller
@RequestMapping("/inventory/")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;
    @Autowired
    RecordService recordService;

    @ApiOperation(value = "批量删除存单信息", notes = "批量删除指定的存单信息")
    @ResponseBody
    @RequestMapping(value = "deletes", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteAll(@RequestBody String  json){
        Map<String, Integer> map = new HashMap();
        List<Long> array = JSON.parseArray(json, Long.class);
        boolean result = inventoryService.removeInventory(array);
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取存单信息", notes = "通过ID获取指定的存单信息")
    @ResponseBody
    @RequestMapping(value = "msg", method = {RequestMethod.POST}, consumes = "application/json")
    public String getInventory(@RequestBody String json){
        Inventory inventory = JSON.parseObject(json, Inventory.class);
        Map<String, Object> map = inventoryService.getInventory(inventory);
        if ( map != null || map.size() > 0){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "删除存单信息", notes = "通过ID删除指定的存单信息")
    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteInventory(@RequestBody String json){
        Inventory inventory = JSON.parseObject(json, Inventory.class);
        Map<String, Object> map = new HashMap();
        boolean b = inventoryService.removeInventory(inventory);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "查找存单信息", notes = "通过指定ID查找存单信息")
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST}, consumes = "application/json")
    public String addInventory(@RequestBody String json){
        Inventory inventory = JSON.parseObject(json, Inventory.class);
        Map<String, Object> map = new HashMap();
        boolean b = inventoryService.addInventory(inventory);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "查找存单信息", notes = "查找存单列表信息")
    @ResponseBody
    @RequestMapping(value = "page/{pagenum}/{stat}", method = {RequestMethod.GET})
    public String getPage(@PathVariable("pagenum") Integer pagenum, @PathVariable("stat") Long stat){
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pagenum);
        List<Inventory> comm = inventoryService.getByPage(pageBean, stat);
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", comm);
        map.put("page", pageBean);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取订单号", notes = "通过UUID随机生成32位订单号码")
    @ResponseBody
    @RequestMapping(value = "uuid", method = {RequestMethod.GET})
    public String getUUID(){
        Map<String, Object> map = new HashMap<>();
        String uuid = UUIDUtils.getUUID();
        map.put("judge",1);
        map.put("uuid",uuid);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "查找指定存单信息", notes = "通过标识符号查找存单信息")
    @ResponseBody
    @RequestMapping(value =  "find", method = {RequestMethod.POST})
    public String getSingle(@RequestBody String json){
        Inventory inventory = JSON.parseObject(json, Inventory.class);
        Inventory singleInventory = inventoryService.getSingleInventory(inventory);
        HashMap<String, Object> map = new HashMap<>();
        map.put("inventory", singleInventory);
        map.put("judge",1);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "存单信息入库", notes = "商品入库")
    @ResponseBody
    @RequestMapping(value = "save", method = {RequestMethod.POST}, consumes = "application/json")
    public String saveInventory(@RequestBody HashMap<String, Object> map){
        boolean b = inventoryService.saveInventory(map);
        HashMap<String, Object> maps = new HashMap<>();
        if (b){
            maps.put("judge",1);
        }else{
            maps.put("judge",0);
        }
        return JSON.toJSONString(maps);
    }

    @ApiOperation(value = "存单信息出库", notes = "商品出库")
    @ResponseBody
    @RequestMapping(value = "out", method = {RequestMethod.POST}, consumes = "application/json")
    public String outInventory(@RequestBody HashMap<String, Object> map){
        boolean b = inventoryService.outInventory(map);
        HashMap<String, Object> maps = new HashMap<>();
        if (b){
            maps.put("judge",1);
        }else{
            maps.put("judge",0);
        }
        return JSON.toJSONString(maps);
    }


}
