package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Role;
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
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findAllById() throws Exception {
        Set<Role> roles = roleRepository.findAllById(2);
        assertEquals(1, roles.size());
    }

    @Test
    public void findByName() throws Exception {
        Role role = roleRepository.findByName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.getName());
    }

}