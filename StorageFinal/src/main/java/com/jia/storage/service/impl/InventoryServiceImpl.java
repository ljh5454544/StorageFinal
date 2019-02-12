package com.jia.storage.service.impl;

import com.jia.storage.entity.Inventory;
import com.jia.storage.entity.Positions;
import com.jia.storage.entity.Record;
import com.jia.storage.entity.Shelf;
import com.jia.storage.mapper.InventoryMapper;
import com.jia.storage.mapper.PositionsMapper;
import com.jia.storage.mapper.RecordMapper;
import com.jia.storage.mapper.ShelfMapper;
import com.jia.storage.service.InventoryService;
import com.jia.storage.service.RecordService;
import com.jia.storage.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    RecordService recordService;

    @Autowired
    ShelfMapper shelfMapper;

    @Autowired
    PositionsMapper positionsMapper;

    @Autowired
    RecordMapper recordMapper;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addInventory(Inventory inventory) {
        if (inventory == null)
            return false;
        int i = inventoryMapper.insertSelective(inventory);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeInventory(Inventory inventory) {
        if (inventory == null || inventory.getId() == null)
            return false;
        boolean b = recordService.removeRecord(inventory.getId());
        int i = inventoryMapper.deleteByPrimaryKey(inventory.getId());
        return b && i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeInventory(List<Long> array) {
        if (array == null || array.size() < 1)
            return false;
        for (Long l : array){
            recordService.removeRecord(l);
            inventoryMapper.deleteByPrimaryKey(l);
        }
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Inventory> getByPage(PageBean pageBean, Long stat) {
        Map map = new HashMap();
        map.put("startRow", (pageBean.getPageNum() - 1) * pageBean.getRowsPerPage());
        map.put("rowsPerPage", pageBean.getRowsPerPage());
        map.put("stat", stat);
        pageBean.setRowsNum(inventoryMapper.selectRowsNum());
        pageBean.setMaxPage( (pageBean.getRowsNum()+pageBean.getRowsPerPage()-1) / pageBean.getRowsPerPage() );
        return inventoryMapper.selectByMap(map);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> getInventory(Inventory inventory) {
        if (inventory == null || inventory.getId() == null)
            return null;
        HashMap<String, Object> map = new HashMap<>();
        Inventory inventory1 = inventoryMapper.selectByPrimaryKey(inventory.getId());
        map.put("inventory", inventory1);
        List<Record> records = recordService.getRecords(inventory.getId());
        map.put("records", records);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Inventory getSingleInventory(Inventory inventory) {
        if (inventory == null || inventory.getIdentity() == null)
            return null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("identity", inventory.getIdentity());
        List<Inventory> list = inventoryMapper.selectByMap(map);
        return list.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateInventory(Inventory inventory) {
        if (inventory == null || inventory.getIdentity() == null)
            return false;
        int i = inventoryMapper.updateByPrimaryKeySelective(inventory);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean saveInventory(Map<String, Object> map) {
        if (map == null)
            return false;
        String id = (String) map.get("id");
        String positions = (String) map.get("location");

        String sid = (String) map.get("sid");
        String pid = (String) map.get("pid");

        Inventory inventory = new Inventory();
        inventory.setId(Long.valueOf(id));
        inventory.setPositions(positions);
        inventory.setStat(2L);
        int i = inventoryMapper.updateByPrimaryKeySelective(inventory);

        Shelf shelf = shelfMapper.selectByPrimaryKey(Long.valueOf(sid));

        Positions positions1 = positionsMapper.selectByPrimaryKey(Long.valueOf(pid));

        Long num = shelf.getNum();
        shelf.setNum(num + 1L);
        i += shelfMapper.updateByPrimaryKeySelective(shelf);

        Inventory inventory1 = inventoryMapper.selectByPrimaryKey(Long.valueOf(id));
        positions1.setCheckempty(1);
        positions1.setSavenum(inventory1.getIdentity());
        i += positionsMapper.updateByPrimaryKeySelective(positions1);

        return i == 3;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean outInventory(Map<String, Object> map) {
        String id = (String) map.get("id");
        String identity = (String) map.get("identity");


        Inventory inventory = new Inventory();
        inventory.setId(Long.valueOf(id));
        inventory.setStat(3L);
        int i = inventoryMapper.updateByPrimaryKeySelective(inventory);

        HashMap<String, Object> maps2 = new HashMap<>();
        maps2.put("savenum", identity);
        List<Positions> list2 = positionsMapper.selectByMap(maps2);
        Positions positions = list2.get(0);
        Long shelfId = positions.getShelfId();
        positions.setCheckempty(0);
        positions.setSavenum("");
        i += positionsMapper.updateByPrimaryKeySelective(positions);

        Shelf shelf = shelfMapper.selectByPrimaryKey(shelfId);
        Long num = shelf.getNum();
        shelf.setNum(num-1L);
        i += shelfMapper.updateByPrimaryKeySelective(shelf);

        return i == 3;
    }
}
