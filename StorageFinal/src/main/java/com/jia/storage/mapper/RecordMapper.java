package com.jia.storage.mapper;

import com.jia.storage.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecordMapper {

    @CacheEvict(value = "record", key = "#p0")
    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    int insertSelective(Record record);

    @Cacheable(value = "record", key = "#p0")
    Record selectByPrimaryKey(Long id);

    @CacheEvict(value = "record", key = "#p0.id")
    int updateByPrimaryKeySelective(Record record);

    @CacheEvict(value = "record", key = "#p0.id")
    int updateByPrimaryKey(Record record);

    int selectRowsNum();

    List<Record> selectByMap(Map map);
}