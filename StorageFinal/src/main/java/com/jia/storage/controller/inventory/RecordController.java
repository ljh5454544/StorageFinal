package com.jia.storage.controller.inventory;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Inventory;
import com.jia.storage.entity.Record;
import com.jia.storage.service.InventoryService;
import com.jia.storage.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(value = "存单详细记录接口", description = "存单的增删改查操作")
@Controller
@RequestMapping("/record/")
public class RecordController {

    @Autowired
    RecordService recordService;
    @Autowired
    InventoryService inventoryService;

    @ApiOperation(value = "批量添加", notes = "批量添加存单详细记录")
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST,RequestMethod.GET}, consumes = "application/json")
    public String addRecord(@RequestBody HashMap<String, Object> map){
        List<String> commArray = (List<String>) map.get("commArray");
        List<String> numArray = (List<String>) map.get("numArray");
        List<String> priceArray = (List<String>) map.get("priceArray");
        String identity = (String) map.get("identity");
        Inventory inventory = new Inventory();
        inventory.setIdentity(identity);
        Inventory singleInventory = inventoryService.getSingleInventory(inventory);
        Long id = singleInventory.getId();

        List<Record> list = new ArrayList<>();
        for (int a = 0; a < commArray.size(); a++){
            Record record = new Record();
            record.setInventoryId(id);
            record.setNum(Long.valueOf(numArray.get(a)));
            record.setCommodityId(Long.valueOf(commArray.get(a)));
            record.setPrice(Long.valueOf(priceArray.get(a)));

            list.add(record);
        }

        boolean b = recordService.addRecord(list);
        HashMap<String, Object> maps = new HashMap<>();
        if ( b ){
            maps.put("judge",1);
            singleInventory.setStat(1L);
            inventoryService.updateInventory(singleInventory);
        }else{
            maps.put("judge",0);
        }
        return JSON.toJSONString(maps);
    }
}
