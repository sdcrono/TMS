package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest//(classes = {RepositoryConfiguration.class})
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findPermissionById() throws Exception {
        Set<Permission> permissions = permissionRepository.findPermissionById(roleRepository.getOne(3).getId());
        assertEquals(4,permissions.size());
    }

}