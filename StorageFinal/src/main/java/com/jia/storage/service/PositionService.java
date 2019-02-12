package com.jia.storage.service;

import com.jia.storage.entity.Positions;
import com.jia.storage.util.PageBean;

import java.util.List;

public interface PositionService {

    boolean addPositions(Positions positions);

    boolean removePositions(Positions positions);

    boolean updatePositions(Positions positions);

    boolean removePositions(List<Long> array);

    List<Positions> getByPage(PageBean pageBean, Long id);

    Positions getPositions(Positions positions);

    List<Positions> getAllPositions();

    List<Positions> getAllPositions(Long id);
}
