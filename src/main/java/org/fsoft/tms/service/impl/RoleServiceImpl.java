package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Role;
import org.fsoft.tms.repository.PermissionRepository;
import org.fsoft.tms.repository.RoleRepository;
import org.fsoft.tms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thehaohcm on 5/30/17.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionService;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findOneRole(int id) {
        return roleRepository.findOne(id);
    }

    @Override
    public void updateRole(Role c) {
        Role temp = roleRepository.findOne(c.getId());
        temp.setName(c.getName());
        roleRepository.save(temp);
    }

    @Override
    public void addPermissionForRole() {
        Role temp = roleRepository.findOne(1);
        temp.setPermissions(permissionService.findPermissionById(1));
        roleRepository.save(temp);
    }
}
