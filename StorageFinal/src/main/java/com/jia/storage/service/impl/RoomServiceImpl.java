package com.jia.storage.service.impl;

import com.jia.storage.entity.Room;
import com.jia.storage.mapper.RoomMapper;
import com.jia.storage.service.RoomService;
import com.jia.storage.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomMapper roomMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addRoom(Room room) {
        if (room == null)
            return false;
        int i = roomMapper.insertSelective(room);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeRoom(Room room) {
        if (room == null || room.getId() == null)
            return false;
        int i = 0;
        try {
            i = roomMapper.deleteByPrimaryKey(room.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateRoom(Room room) {
        if (room == null || room.getId() == null)
            return false;
        int i = roomMapper.updateByPrimaryKeySelective(room);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeRoom(List<Long> array) {
        if (array == null || array.size() < 1)
            return false;
        int count = 0;

        try {
            for (Long l : array){
                count +=roomMapper.deleteByPrimaryKey(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return count == array.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Room> getByPage(PageBean pageBean) {
        Map map = new HashMap();
        map.put("startRow", (pageBean.getPageNum() - 1) * pageBean.getRowsPerPage());
        map.put("rowsPerPage", pageBean.getRowsPerPage());
        pageBean.setRowsNum(roomMapper.selectRowsNum());
        pageBean.setMaxPage( (pageBean.getRowsNum()+pageBean.getRowsPerPage()-1) / pageBean.getRowsPerPage() );
        return roomMapper.selectByMap(map);
    }

    @Cacheable(cacheNames={"room"}, key = "#p0.id")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Room getRoom(Room room) {
        if (room == null || room.getId() == null)
            return null;
        Room result = roomMapper.selectByPrimaryKey(room.getId());
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Room> getAllRoom() {
        List<Room> rooms = roomMapper.selectByMap(new HashMap());
        return rooms;
    }
}
