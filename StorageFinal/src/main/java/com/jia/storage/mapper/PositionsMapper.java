package com.jia.storage.mapper;

import com.jia.storage.entity.Positions;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface PositionsMapper {

    @CacheEvict(value = "position", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Positions record);

    int insertSelective(Positions record);

    @Cacheable(value = "position", key = "#p0")
    Positions selectByPrimaryKey(Long id);

    @CacheEvict(value = "position", key = "#p0.id")
    int updateByPrimaryKeySelective(Positions record);

    @CacheEvict(value = "position", key = "#p0.id")
    int updateByPrimaryKey(Positions record);

    int selectRowsNum();

    List<Positions> selectByMap(Map map);
}