package com.jia.storage.service;

import com.jia.storage.entity.Inventory;
import com.jia.storage.util.PageBean;

import java.util.List;
import java.util.Map;

public interface InventoryService {

    boolean addInventory(Inventory inventory);

    boolean removeInventory(Inventory inventory);

    boolean removeInventory(List<Long> array);

    List<Inventory> getByPage(PageBean pageBean, Long stat);

    Map<String, Object> getInventory(Inventory inventory);

    Inventory getSingleInventory(Inventory inventory);

    boolean updateInventory(Inventory inventory);

    boolean saveInventory(Map<String, Object> map);

    boolean outInventory(Map<String, Object> map);
}
