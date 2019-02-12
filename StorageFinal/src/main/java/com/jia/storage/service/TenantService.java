package com.jia.storage.service;

import com.jia.storage.entity.Tenant;
import com.jia.storage.util.PageBean;

import java.util.List;

public interface TenantService {

    boolean addCommodity(Tenant comm);

    boolean removeCommdity(Tenant comm);

    boolean updateCommdity(Tenant comm);

    boolean removeCommdity(List<Long> array) throws ArrayIndexOutOfBoundsException;

    List<Tenant> getByPage(PageBean pageBean);

    Tenant getCommdity(Tenant comm);

    boolean addCommoditys(List<Tenant> list);

    List<Tenant> getAllTenant();
}
