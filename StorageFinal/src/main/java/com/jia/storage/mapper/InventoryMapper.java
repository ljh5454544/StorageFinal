package com.jia.storage.mapper;

import com.jia.storage.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface InventoryMapper {

    @CacheEvict(value = "inventory", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    @Cacheable(value = "inventory", key = "#p0")
    Inventory selectByPrimaryKey(Long id);

    @CacheEvict(value = "inventory", key = "#p0.id")
    int updateByPrimaryKeySelective(Inventory record);

    @CacheEvict(value = "inventory", key = "#p0.id")
    int updateByPrimaryKey(Inventory record);

    int selectRowsNum();

    List<Inventory> selectByMap(Map map);
}