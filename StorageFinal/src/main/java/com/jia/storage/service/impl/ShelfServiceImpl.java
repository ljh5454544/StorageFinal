package com.jia.storage.service.impl;

import com.jia.storage.entity.Shelf;
import com.jia.storage.mapper.ShelfMapper;
import com.jia.storage.service.ShelfService;
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
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    ShelfMapper shelfMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addShelf(Shelf shelf) {
        if (shelf == null)
            return false;
        int i = shelfMapper.insertSelective(shelf);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeShelf(Shelf shelf) {
        if (shelf == null || shelf.getId() == null)
            return false;
        int i = shelfMapper.deleteByPrimaryKey(shelf.getId());
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateShelf(Shelf shelf) {
        if (shelf == null || shelf.getId() == null)
            return false;
        int i = shelfMapper.updateByPrimaryKeySelective(shelf);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeShelf(List<Long> array) {
        if (array == null || array.size() < 1)
            return false;
        int count = 0;
        for (Long l : array){
            count += shelfMapper.deleteByPrimaryKey(l);
        }
        return count == array.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Shelf> getByPage(PageBean pageBean, Long id) {
        Map map = new HashMap();
        map.put("startRow", (pageBean.getPageNum() - 1) * pageBean.getRowsPerPage());
        map.put("rowsPerPage", pageBean.getRowsPerPage());
        map.put("roomId", id);
        pageBean.setRowsNum(shelfMapper.selectRowsNum());
        pageBean.setMaxPage( (pageBean.getRowsNum()+pageBean.getRowsPerPage()-1) / pageBean.getRowsPerPage() );
        return shelfMapper.selectByMap(map);
    }

    @Cacheable(cacheNames={"shelf"}, key = "#p0.id")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Shelf getShelf(Shelf shelf) {
        if (shelf == null || shelf.getId() == null)
            return null;
        Shelf shelf1 = shelfMapper.selectByPrimaryKey(shelf.getId());
        return shelf1;
    }

    @Override
    public List<Shelf> getAllShelf() {
        List<Shelf> list = shelfMapper.selectByMap(new HashMap());
        return list;
    }

    @Override
    public List<Shelf> getAllShelf(Long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("roomId", id);
        List<Shelf> list = shelfMapper.selectByMap(new HashMap());
        return list;
    }
}

