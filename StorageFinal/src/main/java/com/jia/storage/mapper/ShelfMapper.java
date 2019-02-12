package com.jia.storage.mapper;

import com.jia.storage.entity.Shelf;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShelfMapper {

    @CacheEvict(value = "shelf", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Shelf record);

    int insertSelective(Shelf record);

    @Cacheable(value = "shelf", key = "#p0")
    Shelf selectByPrimaryKey(Long id);

    @CacheEvict(value = "shelf", key = "#p0.id")
    int updateByPrimaryKeySelective(Shelf record);

    @CacheEvict(value = "shelf", key = "#p0.id")
    int updateByPrimaryKey(Shelf record);

    int selectRowsNum();

    List<Shelf> selectByMap(Map map);
}