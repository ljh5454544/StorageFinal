package com.jia.storage.service.impl;

import com.jia.storage.entity.Record;
import com.jia.storage.mapper.RecordMapper;
import com.jia.storage.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordMapper recordMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addRecord(Record record) {
        if (record == null)
            return false;
        int i = recordMapper.insertSelective(record);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addRecord(List<Record> list) {
        if (list == null || list.size() < 1){
            return false;
        }
        int count = 0;
        for (Record r : list){
            count += recordMapper.insertSelective(r);
        }
        return count == list.size();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeRecord(Record record) {
        if (record == null || record.getId() == null)
            return false;
        int i = recordMapper.deleteByPrimaryKey(record.getId());
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateRecord(Record record) {
        if (record == null || record.getId() == null)
            return false;
        int i = recordMapper.updateByPrimaryKeySelective(record);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeRecord(Long id) {
        if (id < 0)
            return false;
        List<Record> list = getRecords(id);
        int count = 0;
        for (Record r : list){
            count += recordMapper.deleteByPrimaryKey(r.getId());
        }
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Record> getRecords(Long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("inventoryId", id);
        List<Record> list = recordMapper.selectByMap(map);
        return list;
    }
}
