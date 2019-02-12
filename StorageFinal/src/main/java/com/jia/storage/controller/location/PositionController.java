package com.jia.storage.controller.location;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Positions;
import com.jia.storage.service.PositionService;
import com.jia.storage.util.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "区位接口", description = "操作区位的增删改查等操作")
@Controller
@RequestMapping("/position/")
public class PositionController {

    @Autowired
    PositionService positionService;

    @ApiOperation(value = "区位批量删除", notes = "批量删除所有指定的区位")
    @ResponseBody
    @RequestMapping(value = "deletes", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteAll(@RequestBody String  json){
        Map<String, Integer> map = new HashMap();
        List<Long> array = JSON.parseArray(json, Long.class);
        boolean result = positionService.removePositions(array);
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "修改区位", notes = "批量添加存单详细记录")
    @ResponseBody
    @RequestMapping(value = "update", method = {RequestMethod.POST}, consumes = "application/json")
    public String updateCommodity(@RequestBody String json){
        Positions positions = JSON.parseObject(json, Positions.class);
        Map<String, Integer> map = new HashMap();
        boolean result = positionService.updatePositions(positions);
        if ( result ){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "查看区位信息", notes = "查看指定ID的区位信息")
    @ResponseBody
    @RequestMapping(value = "msg", method = {RequestMethod.POST}, consumes = "application/json")
    public String getComm(@RequestBody String json){
        Positions positions = JSON.parseObject(json, Positions.class);
        Map<String, Object> map = new HashMap();
        Positions position = positionService.getPositions(positions);
        if ( position != null || position.getId() != null){
            map.put("judge", 1);
            map.put("position", position);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "删除区位", notes = "删除指定ID的区位信息")
    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteComm(@RequestBody String json){
        Positions positions = JSON.parseObject(json, Positions.class);
        Map<String, Object> map = new HashMap();
        boolean b = positionService.removePositions(positions);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "添加区位", notes = "添加区位")
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST}, consumes = "application/json")
    public String addComm(@RequestBody String json){
        Positions positions = JSON.parseObject(json, Positions.class);
        Map<String, Object> map = new HashMap();
        boolean b = positionService.addPositions(positions);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "查看区位列表", notes = "查看指定页码的区位列表")
    @ResponseBody
    @RequestMapping(value = "page/{pagenum}/{id}", method = {RequestMethod.GET})
    public String getPage(@PathVariable("pagenum") Integer pagenum, @PathVariable("id") Long id){
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pagenum);
        List<Positions> comm = positionService.getByPage(pageBean, id);
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", comm);
        map.put("page", pageBean);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "查看所有区位", notes = "查看所有区位")
    @ResponseBody
    @RequestMapping(value = "all", method = {RequestMethod.GET})
    public String getAllPostion(){
        List<Positions> allPositions = positionService.getAllPositions();
        return JSON.toJSONString(allPositions);
    }

    @ApiOperation(value = "查看所有区位", notes = "查看所有指定shelf的ID的区位")
    @ResponseBody
    @RequestMapping(value = "sposition/{id}", method = {RequestMethod.GET})
    public String getAllSPostion(@PathVariable("id") Long id){
        List<Positions> allPositions = positionService.getAllPositions(id);
        return JSON.toJSONString(allPositions);
    }
}
