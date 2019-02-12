package com.jia.storage.mapper;

import com.jia.storage.entity.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface RolesMapper {

    @CacheEvict(value = "role", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Roles record);

    int insertSelective(Roles record);

    @Cacheable(value = "role", key = "#p0")
    Roles selectByPrimaryKey(Long id);

    @CacheEvict(value = "role", key = "#p0.id")
    int updateByPrimaryKeySelective(Roles record);

    @CacheEvict(value = "role", key = "#p0.id")
    int updateByPrimaryKey(Roles record);

    List<Roles> selectByMap(Map map);
}