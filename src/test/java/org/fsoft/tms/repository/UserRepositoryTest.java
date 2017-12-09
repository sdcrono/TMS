package org.fsoft.tms.repository;

import org.fsoft.tms.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 28-May-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest//(classes = {RepositoryConfiguration.class})
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
//    public void testSaveCategory(){
//
//        User user = new User("Thanh",passwordEncoder.encode("Thanh"));
//        user.setRole(roleRepository.findOne(4));
//        user.setManager(userRepository.findUserByUsername("nv001"));
//        user.setActive(true);
//        assertNull(user.getId());
//        userRepository.save(user);
//        assertNotNull(user.getId());
//
//        User fetchedUser = userRepository.findOne(user.getId());
//        assertNotNull(fetchedUser);
//        assertEquals(user.getId(), fetchedUser.getId());
//        assertEquals(user.getUsername(), fetchedUser.getUsername());
//        assertEquals(user.getPassword(), fetchedUser.getPassword());
//
////        fetchedUser.setPassword(passwordEncoder.encode("Thanh1"));
////        userRepository.save(fetchedUser);
////        User fetchedUpdatedUser = userRepository.findOne(fetchedUser.getId());
////        assertEquals(fetchedUser.getPassword(), fetchedUpdatedUser.getPassword());
//
//        long userCount = userRepository.count();
//        assertEquals(userCount, 6);
//
//        Iterable<User> categories = userRepository.findAll();
//        int count = 0;
//        for(User c : categories){
//            count++;
//        }
//        assertEquals(count, 6);
//
////        fetchedUser.setActive(false);
////        userRepository.save(fetchedUser);
////        User fetchedDeletedUser = userRepository.findOne(fetchedUser.getId());
////        assertEquals(fetchedDeletedUser.isActive(), false);
//
//    }

    @Test
    public void findAllById() throws Exception {
        User user = userRepository.findAllById(1);
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void findUserByUsername() throws Exception {
        User user = userRepository.findUserByUsername("admin");
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void findAllByRole() throws Exception {
        List<User> users = userRepository.findAllByRole(roleRepository.findOne(2));
        assertEquals(2, users.size());
    }

    @Test
    public void findAllByRoleAndManager() throws Exception {
        List<User> users = userRepository.findAllByRoleAndManager(roleRepository.findOne(3), userRepository.findOne(2));
        assertEquals(1, users.size());
    }

    @Test
    public void findAllByUsername() throws Exception {
        User user = userRepository.findAllByUsername("hv001");
        assertEquals("hv001", user.getUsername());
    }

}