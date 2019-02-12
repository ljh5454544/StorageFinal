package com.jia.storage.controller.location;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Shelf;
import com.jia.storage.service.ShelfService;
import com.jia.storage.util.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "货架接口", description = "货架信息接口")
@Controller
@RequestMapping("/shelf/")
public class ShelfController {

    @Autowired
    ShelfService shelfService;

    @ApiOperation(value = "批量删除货架信息", notes = "删除指定List的货架信息")
    @ResponseBody
    @RequestMapping(value = "deletes", method = {RequestMethod.POST,RequestMethod.GET}, consumes = "application/json")
    public String deleteAll(@RequestBody String  json){
        Map<String, Integer> map = new HashMap();
        List<Long> array = JSON.parseArray(json, Long.class);
        boolean result = shelfService.removeShelf(array);
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "更新货架信息", notes = "更新指定ID的货架信息")
    @ResponseBody
    @RequestMapping(value = "update", method = {RequestMethod.POST,RequestMethod.GET}, consumes = "application/json")
    public String updateCommodity(@RequestBody String json){
        Shelf shelf = JSON.parseObject(json, Shelf.class);

        System.out.println(shelf);
        Map<String, Integer> map = new HashMap();
        boolean result = shelfService.updateShelf(shelf);
        if ( result ){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取货架信息", notes = "获取指定ID的货架信息")
    @ResponseBody
    @RequestMapping(value = "msg", method = {RequestMethod.POST,RequestMethod.GET}, consumes = "application/json")
    public String getComm(@RequestBody String json){
        Shelf shelf = JSON.parseObject(json, Shelf.class);
        Map<String, Object> map = new HashMap();
        Shelf shelf1 = shelfService.getShelf(shelf);
        if ( shelf1 != null || shelf1.getId() != null){
            map.put("judge", 1);
            map.put("shelf", shelf1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "删除货架信息", notes = "删除指定ID的货架信息")
    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.POST,RequestMethod.GET}, consumes = "application/json")
    public String deleteComm(@RequestBody String json){
        Shelf shelf = JSON.parseObject(json, Shelf.class);
        Map<String, Object> map = new HashMap();
        boolean b = shelfService.removeShelf(shelf);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "添加货架信息", notes = "添加货架信息")
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST}, consumes = "application/json")
    public String addComm(@RequestBody String json){
        Shelf shelf = JSON.parseObject(json, Shelf.class);
        Map<String, Object> map = new HashMap();
        boolean b = shelfService.addShelf(shelf);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取货架分页信息", notes = "获取指定页码的货架信息")
    @ResponseBody
    @RequestMapping("page/{pagenum}/{id}")
    public String getPage(@PathVariable("pagenum") Integer pagenum,@PathVariable("id") Long id){
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pagenum);
        List<Shelf> comm = shelfService.getByPage(pageBean, id);
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", comm);
        map.put("page", pageBean);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取全部货架信息", notes = "获取全部货架信息")
    @ResponseBody
    @RequestMapping(value = "all", method = {RequestMethod.GET})
    public String getAllShelf(){
        List<Shelf> allShelf = shelfService.getAllShelf();
        return JSON.toJSONString(allShelf);
    }

    @ApiOperation(value = "获取指定货架信息", notes = "获取指定库区ID的全部货架信息")
    @ResponseBody
    @RequestMapping(value = "rshlef/{id}", method = {RequestMethod.GET})
    public String getAllRShelf(@PathVariable("id") Long id){
        List<Shelf> allShelf = shelfService.getAllShelf(id);
        return JSON.toJSONString(allShelf);
    }
}
