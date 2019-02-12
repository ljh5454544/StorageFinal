package com.jia.storage.mapper;

import com.jia.storage.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoomMapper {

    @CacheEvict(value = "room", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Room record);

    int insertSelective(Room record);

    @Cacheable(value = "room", key = "#p0")
    Room selectByPrimaryKey(Long id);

    @CacheEvict(value = "room", key = "#p0.id")
    int updateByPrimaryKeySelective(Room record);

    @CacheEvict(value = "room", key = "#p0.id")
    int updateByPrimaryKey(Room record);

    int selectRowsNum();

    List<Room> selectByMap(Map map);
}