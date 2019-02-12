package com.jia.storage.service.impl;

import com.jia.storage.entity.Roles;
import com.jia.storage.mapper.RolesMapper;
import com.jia.storage.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    RolesMapper rolesMapper;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Roles> getAllRoles() {
        return rolesMapper.selectByMap(null);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Roles getRolesById(Long id) {
        return rolesMapper.selectByPrimaryKey(id);
    }
}
