package com.jia.storage.service;

import com.jia.storage.entity.Commodity;
import com.jia.storage.util.PageBean;

import java.util.List;

public interface CommoditySerivce {

    boolean addCommodity(Commodity comm);

    boolean removeCommdity(Commodity comm);

    boolean updateCommdity(Commodity comm);

    boolean removeCommdity(List<Long> array) throws ArrayIndexOutOfBoundsException;

    List<Commodity> getByPage(PageBean pageBean);

    Commodity getCommdity(Commodity comm);

    boolean addCommoditys(List<Commodity> list);

    List<Commodity> getALL();
}
