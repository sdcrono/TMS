package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Permission;
import org.fsoft.tms.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionServiceImplTest {

    @Autowired
    private PermissionServiceImpl permissionService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void getAllPermission() throws Exception {
        List<Permission> permissions = permissionService.getAllPermission();
        assertEquals(37, permissions.size());
    }

    @Test
    public void addPermission() throws Exception {
        Permission permission = new Permission("Credit","log into the credit page", roleRepository.findAllById(4));
        permissionService.addPermission(permission);
        Permission permission1 = permissionService.findOnePermisison(permission.getId());
        assertEquals("Credit", permission1.getName());
    }

    @Test
    public void findOnePermisison() throws Exception {
        Permission permission = permissionService.findOnePermisison(4);
        assertNotNull(permission);
    }

    @Test
    public void updatePermission() throws Exception {
        Permission permission = permissionService.findOnePermisison(39);
        permission.setName("Credit1");
        permissionService.updatePermission(permission);
        Permission permission1 = permissionService.findOnePermisison(39);
        assertEquals("Credit1", permission1.getName());
    }

}