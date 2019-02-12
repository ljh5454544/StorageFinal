package com.jia.storage.mapper;

import com.jia.storage.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersMapper {

    @CacheEvict(value = "user", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    @Cacheable(value = "user", key = "#p0")
    Users selectByPrimaryKey(Long id);

    @CacheEvict(value = "user", key = "#p0.id")
    int updateByPrimaryKeySelective(Users record);

    @CacheEvict(value = "user", key = "#p0.id")
    int updateByPrimaryKey(Users record);

    int selectRowsNum();

    List<Users> selectByMap(Map map);

}