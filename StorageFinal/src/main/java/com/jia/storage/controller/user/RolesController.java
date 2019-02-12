package com.jia.storage.controller.user;


import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Roles;
import com.jia.storage.service.RolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value = "职位请求接口")
@Controller
@RequestMapping("/role")
public class RolesController {

    @Autowired
    RolesService rolesService;

    @ApiOperation(value = "获取全部的职位信息", notes = "三大职位信息")
    @ResponseBody
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public String getRoles(){
        List<Roles> list = rolesService.getAllRoles();
        return JSON.toJSONString(list);
    }

    @ApiOperation(value = "获取指定职位信息", notes = "通过指定的ID获取职位信息")
    @ResponseBody
    @RequestMapping("/single/{id}")
    public String getRole(@PathVariable("id") Long id){
        Roles role = rolesService.getRolesById(id);
        return JSON.toJSONString(role);
    }

}
