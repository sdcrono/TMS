package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Permission;
import org.fsoft.tms.repository.PermissionRepository;
import org.fsoft.tms.repository.RoleRepository;
import org.fsoft.tms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thehaohcm on 5/30/17.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Permission> getAllPermission() {
        return permissionRepository.findAll();
    }

    @Override
    public void addPermission(Permission role) {
        permissionRepository.save(role);
    }

    @Override
    public Permission findOnePermisison(int id) {
        return permissionRepository.findOne(id);
    }

    @Override
    public void updatePermission(Permission c) {
        Permission temp = permissionRepository.findOne(c.getId());
        temp.setName(c.getName());
        temp.setDescription(c.getDescription());
        permissionRepository.save(temp);
    }

    @Override
    public void addRole() {
        Permission temp = permissionRepository.findOne(1);
        temp.setRoles(roleRepository.findAllById(1));
        permissionRepository.save(temp);
    }
}
