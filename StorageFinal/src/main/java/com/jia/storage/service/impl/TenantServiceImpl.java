package com.jia.storage.service.impl;

import com.jia.storage.entity.Tenant;
import com.jia.storage.mapper.TenantMapper;
import com.jia.storage.service.TenantService;
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
public class TenantServiceImpl implements TenantService {

    @Autowired
    TenantMapper tenantMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addCommodity(Tenant comm) {
        if (comm == null)
            return false;
        int i = tenantMapper.insertSelective(comm);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeCommdity(Tenant comm) {
        if (comm == null || comm.getId() == null)
            return false;
        int i = tenantMapper.deleteByPrimaryKey(comm.getId());
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateCommdity(Tenant comm) {
        if (comm == null || comm.getId() == null)
            return false;
        int i = tenantMapper.updateByPrimaryKeySelective(comm);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeCommdity(List<Long> array){
        if (array == null || array.size() < 1){
            return false;
        }
        int count = 0;
        for (Long l : array){
            count += tenantMapper.deleteByPrimaryKey(l);
        }
        return count == array.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Tenant> getByPage(PageBean pageBean) {
        Map map = new HashMap();
        map.put("startRow", (pageBean.getPageNum() - 1) * pageBean.getRowsPerPage());
        map.put("rowsPerPage", pageBean.getRowsPerPage());
        pageBean.setRowsNum(tenantMapper.selectRowsNum());
        pageBean.setMaxPage( (pageBean.getRowsNum()+pageBean.getRowsPerPage()-1) / pageBean.getRowsPerPage() );
        return tenantMapper.selectByMap(map);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Tenant getCommdity(Tenant comm) {
        if (comm == null || comm.getId() == null)
            return null;
        Tenant tenant = tenantMapper.selectByPrimaryKey(comm.getId());
        return tenant;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean addCommoditys(List<Tenant> list) {
        if (list == null || list.size() < 1)
            return false;
        int count = 0;
        for (Tenant l : list){
            count += tenantMapper.insertSelective(l);
        }
        return count == list.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Tenant> getAllTenant() {
        List<Tenant> tenants = tenantMapper.selectByMap(new HashMap());
        return tenants;
    }
}
