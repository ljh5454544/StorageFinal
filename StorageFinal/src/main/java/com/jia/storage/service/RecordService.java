package com.jia.storage.service;

import com.jia.storage.entity.Record;

import java.util.List;

public interface RecordService {

    boolean addRecord(Record record);

    boolean addRecord(List<Record> list);

    boolean removeRecord(Record record);

    boolean updateRecord(Record record);

    boolean removeRecord(Long id);

    List<Record> getRecords(Long id);
}
