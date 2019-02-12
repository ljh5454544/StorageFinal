package com.jia.storage.controller.user;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Users;
import com.jia.storage.service.UsersService;
import com.jia.storage.util.MD5Utils;
import com.jia.storage.util.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(value = "用户接口", description = "和Users相关的所有操作")
@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "退出登录的账户", notes = "通过前端的token和username进行token缓存的清除")
    @ResponseBody
    @RequestMapping(value = "/exit", method = {RequestMethod.POST}, consumes = "application/json")
    public String exit(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        Boolean result = stringRedisTemplate.delete(username);
        HashMap<String, Object> maps = new HashMap<>();
        if (result){
            maps.put("judge", 1);
        }else{
            maps.put("judge", 0);
        }
        return JSON.toJSONString(maps);
    }


    @ApiOperation(value = "批量删除Users", notes = "通过提交上来的List 批量删除users")
    @ResponseBody
    @RequestMapping(value = "/deletes", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteAll(@RequestBody String json) throws ArrayIndexOutOfBoundsException {
        List<Long> array = JSON.parseArray(json, Long.class);
        boolean result = usersService.removeUsers(array);
        Map<java.lang.String, Integer> map = new HashMap();
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "更新Users", notes = "根据提交上来的userID进行更新")
    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST}, consumes = "application/json")
    public String updateUser(@RequestBody String json) throws NoSuchAlgorithmException {
        Users users = JSON.parseObject(json, Users.class);
        Map<String, Integer> map = new HashMap();
        boolean result = usersService.updateUser(users);
        if (result){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "获取指定ID的Users的信息")
    @ResponseBody
    @RequestMapping(value = "/msg", method = {RequestMethod.POST}, consumes = "application/json")
    public String getUser(@RequestBody String json){
        Users users = JSON.parseObject(json, Users.class);
        Map<String, Object> map = new HashMap();
        Users result = usersService.getUsers(users);
        if ( result != null && result.getId() != null){
            map.put("judge", 1);
            map.put("user", result);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "Users登录")
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET}, consumes = "application/json")
    public String login(@RequestBody String json) throws NoSuchAlgorithmException {
        Users users = JSON.parseObject(json, Users.class);
        Users result = usersService.login(users);
        Map<String, Object> map = new HashMap();


        if ( result.getId() != null ){
            // 这里应该做一个登录指纹返回给访问端
            String hash = MD5Utils.encrypt(result.getUsername() + new Date());
            // 存入Redis
            stringRedisTemplate.opsForValue().set(result.getUsername(), hash, 60*30, TimeUnit.SECONDS);
            map.put("judge", 1);
            map.put("token", hash);
            map.put("username", result.getUsername());
            map.put("id", result.getId());
            map.put("role", result.getRoleId());
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }

    @ApiOperation(value = "删除指定的Users")
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST}, consumes = "application/json")
    public String deleteUser(@RequestBody String json){
        Users users = JSON.parseObject(json, Users.class);
        Map<String, Object> map = new HashMap();
        boolean b = usersService.removeUser(users);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }


    @ApiOperation(value = "添加Users")
    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST}, consumes = "application/json")
    public String addUser(@RequestBody String json) throws NoSuchAlgorithmException {
        Users users = JSON.parseObject(json, Users.class);
        Map<String, Object> map = new HashMap();

        boolean b = usersService.registUser(users);
        if (b){
            map.put("judge", 1);
        }else{
            map.put("judge", 0);
        }
        return JSON.toJSONString(map);
    }


    @ApiOperation(value = "获取Users分页信息")
    @ResponseBody
    @RequestMapping(value = "/page/{pagenum}", method = {RequestMethod.GET})
    public String getPage(@PathVariable("pagenum") Integer pagenum){
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pagenum);
        List<Users> list = usersService.getByPage(pageBean);
        Map<String, Object> map = new HashMap<>();
        map.put("judge",1);
        map.put("list", list);
        map.put("page", pageBean);
        return JSON.toJSONString(map);
    }
}
