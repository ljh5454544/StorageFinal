package com.jia.storage.service.impl;

import com.jia.storage.entity.Positions;
import com.jia.storage.mapper.PositionsMapper;
import com.jia.storage.service.PositionService;
import com.jia.storage.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    PositionsMapper positionsMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addPositions(Positions positions) {
        if(positions == null)
            return false;
        int i = positionsMapper.insertSelective(positions);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removePositions(Positions positions) {
        if (positions == null || positions.getId() == null)
            return false;
        int i = positionsMapper.deleteByPrimaryKey(positions.getId());
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updatePositions(Positions positions) {
        if (positions == null || positions.getId() == null)
            return false;
        int i = positionsMapper.updateByPrimaryKeySelective(positions);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removePositions(List<Long> array) {
        if (array == null || array.size() < 1)
            return false;
        int count = 0;
        for (Long l : array){
            count += positionsMapper.deleteByPrimaryKey(l);
        }
        return count == array.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Positions> getByPage(PageBean pageBean, Long id) {
        Map map = new HashMap();
        map.put("startRow", (pageBean.getPageNum() - 1) * pageBean.getRowsPerPage());
        map.put("rowsPerPage", pageBean.getRowsPerPage());
        map.put("shelfId", id);
        pageBean.setRowsNum(positionsMapper.selectRowsNum());
        pageBean.setMaxPage( (pageBean.getRowsNum()+pageBean.getRowsPerPage()-1) / pageBean.getRowsPerPage() );
        return positionsMapper.selectByMap(map);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Positions getPositions(Positions positions) {
        if (positions == null || positions.getId() == null)
            return null;
        Positions positions1 = positionsMapper.selectByPrimaryKey(positions.getId());
        return positions1;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Positions> getAllPositions() {
        List<Positions> list = positionsMapper.selectByMap(new HashMap());
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Positions> getAllPositions(Long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("shelfId" , id);
        map.put("checkempty" , 0);
        List<Positions> list = positionsMapper.selectByMap(map);
        return list;
    }
}
