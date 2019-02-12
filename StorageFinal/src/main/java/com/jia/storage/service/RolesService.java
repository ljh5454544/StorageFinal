package com.jia.storage.service;

import com.jia.storage.entity.Roles;

import java.util.List;

public interface RolesService {

    List<Roles> getAllRoles();

    Roles getRolesById(Long id);

}
