package com.jia.storage.controller.location;


import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Room;
import com.jia.storage.service.RoomService;
import com.jia.storage.util.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "库区接口", description = "库区操作")
@Controller
@RequestMapping("/room/")
public class RoomController {

    @Autowired
    RoomService roomService;

    @ApiOperation(value = "批量删除库区", notes = "通过指定的List删除库区")
    @ResponseBody
    @RequestMapping(value = "deletes", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteAll(@RequestBody String  json){
        Map<String, Integer> map = new HashMap();
        List<Long> array = JSON.parseArray(json, Long.class);
        boolean result = roomService.removeRoom(array);
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "更新库区", notes = "通过指定的ID更新库区")
    @ResponseBody
    @RequestMapping(value = "update", method = {RequestMethod.POST}, consumes = "application/json")
    public String updateCommodity(@RequestBody String json){
        Room room = JSON.parseObject(json, Room.class);

        System.out.println(room);
        Map<String, Integer> map = new HashMap();
        boolean result = roomService.updateRoom(room);
        if ( result ){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取库区信息", notes = "通过指定ID获取对应的库区的信息")
    @ResponseBody
    @RequestMapping(value = "msg", method = {RequestMethod.POST}, consumes = "application/json")
    public String getComm(@RequestBody String json){
        Room room = JSON.parseObject(json, Room.class);
        Map<String, Object> map = new HashMap();
        Room room1 = roomService.getRoom(room);
        if ( room1 != null || room1.getId() != null){
            map.put("judge", 1);
            map.put("room", room1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "删除库区信息", notes = "通过指定ID删除库区的信息")
    @ResponseBody
    @RequestMapping(value = "delete", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteComm(@RequestBody String json){
        Room room = JSON.parseObject(json, Room.class);
        Map<String, Object> map = new HashMap();
        boolean b = roomService.removeRoom(room);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "添加库区信息", notes = "添加库区信息")
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST}, consumes = "application/json")
    public String addComm(@RequestBody String json){
        Room room = JSON.parseObject(json, Room.class);
        Map<String, Object> map = new HashMap();
        boolean b = roomService.addRoom(room);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "库区分页信息", notes = "通过指定页码查看库区的信息")
    @ResponseBody
    @RequestMapping(value = "page/{pagenum}", method = {RequestMethod.GET})
    public String getPage(@PathVariable("pagenum") Integer pagenum){
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pagenum);
        List<Room> comm = roomService.getByPage(pageBean);
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", comm);
        map.put("page", pageBean);
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取所有库区信息", notes = "获取全部库区信息")
    @ResponseBody
    @RequestMapping(value = "all", method = {RequestMethod.GET})
    public String getAllRoom(){
        List<Room> allRoom = roomService.getAllRoom();
        return JSON.toJSONString(allRoom);
    }


}
