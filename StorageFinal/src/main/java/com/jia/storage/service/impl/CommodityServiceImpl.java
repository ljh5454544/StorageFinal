package com.jia.storage.service.impl;

import com.jia.storage.entity.Commodity;
import com.jia.storage.mapper.CommodityMapper;
import com.jia.storage.service.CommoditySerivce;
import com.jia.storage.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommodityServiceImpl implements CommoditySerivce {

    @Autowired
    CommodityMapper commodityMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addCommodity(Commodity comm) {
        if (comm == null)
            return false;
        int i = commodityMapper.insertSelective(comm);
        return i > 0;
    }

    @CacheEvict(value = "commodity", key = "#p0.id")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeCommdity(Commodity comm) {
        if (comm == null || comm.getId() == null)
            return false;
        int i = commodityMapper.deleteByPrimaryKey(comm.getId());
        return i > 0;
    }

    @CacheEvict(value = "commodity", key = "#p0.id")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateCommdity(Commodity comm) {
        if (comm == null || comm.getId() == null)
            return false;
        int i = commodityMapper.updateByPrimaryKeySelective(comm);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeCommdity(List<Long> array){
        int count = 0;
        if (array == null || array.size() < 1)
            return false;
        for (Long u : array){
            count += commodityMapper.deleteByPrimaryKey(u);
        }
        if (count != array.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return count == array.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Commodity> getByPage(PageBean pageBean) {
        Map map = new HashMap();
        map.put("startRow", (pageBean.getPageNum() - 1) * pageBean.getRowsPerPage());
        map.put("rowsPerPage", pageBean.getRowsPerPage());
        pageBean.setRowsNum(commodityMapper.selectRowsNum());
        pageBean.setMaxPage( (pageBean.getRowsNum()+pageBean.getRowsPerPage()-1) / pageBean.getRowsPerPage() );
        return commodityMapper.selectByMap(map);
    }

    @Cacheable(value = "commodity", key = "#p0.id")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Commodity getCommdity(Commodity comm) {
        if (comm == null || comm.getId() == null)
            return null;
        Commodity commodity = commodityMapper.selectByPrimaryKey(comm.getId());
        return commodity;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addCommoditys(List<Commodity> list){
        if (list == null || list.size() < 1)
            return false;
        int count = 0;
        for (Commodity c : list){
            count += commodityMapper.insertSelective(c);
        }
        if (count != list.size()){
            throw new RuntimeException();
        }
        return count == list.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Commodity> getALL() {
        List<Commodity> commodities = commodityMapper.selectByMap(new HashMap());
        return commodities;
    }
}
