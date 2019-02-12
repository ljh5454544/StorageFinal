package com.jia.storage.mapper;

import com.jia.storage.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommodityMapper {

    @CacheEvict(value = "commodity", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    @Cacheable(value = "commodity", key = "#p0")
    Commodity selectByPrimaryKey(Long id);

    @CacheEvict(value = "commodity", key = "#p0.id")
    int updateByPrimaryKeySelective(Commodity record);

    @CacheEvict(value = "commodity", key = "#p0.id")
    int updateByPrimaryKey(Commodity record);

    int selectRowsNum();

    List<Commodity> selectByMap(Map map);
}