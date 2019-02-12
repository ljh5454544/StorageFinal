package com.jia.storage.mapper;

import com.jia.storage.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface TenantMapper {

    @CacheEvict(value = "tenant", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Tenant record);

    int insertSelective(Tenant record);

    @Cacheable(value = "tenant", key = "#p0")
    Tenant selectByPrimaryKey(Long id);

    @CacheEvict(value = "tenant", key = "#p0.id")
    int updateByPrimaryKeySelective(Tenant record);

    @CacheEvict(value = "tenant", key = "#p0.id")
    int updateByPrimaryKey(Tenant record);

    int selectRowsNum();

    List<Tenant> selectByMap(Map map);

}