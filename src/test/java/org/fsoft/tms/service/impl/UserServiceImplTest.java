package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.TraineeInfo;
import org.fsoft.tms.entity.TrainerInfo;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.entity.UserProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired private CourseServiceImpl courseService;

    @Autowired private PasswordEncoder passwordEncoder;


    @Test
    public void getAllUser() throws Exception {
        List<User> users = userService.getAllUser();
        assertEquals(5, users.size());
    }

    @Test
    public void getAllUserByRole() throws Exception {
        List<User> users = userService.getAllUserByRole(1);
        assertEquals(1, users.size());
    }

    @Test
    public void addUser() throws Exception {
        User user = new User("nv009", "nv009");
        userService.addUser(user, 2, userService.findOneUser(1).getId());
        User staff = userService.findOneUser(user.getId());
        assertNotNull(staff);
    }

    @Test
    public void addTrainee() throws Exception {
        User user = new User("hv009", "hv009");
        userService.addTrainee(user, 2);
        User user1 = userService.findOneUser(user.getId());
        assertNotNull(user1);
        int id = user1.getManager().getId();
        assertEquals(2, id);
    }

    @Test
    public void findOneUser() throws Exception {
        User user = userService.findOneUser(1);
        assertNotNull(user);
    }

    @Test
    public void updateUser() throws Exception {
        User user = userService.findOneUser(4);
        user.setPassword("ABC");
        userService.updateUser(user);
        User user1 = userService.findOneUser(4);
        assertEquals(passwordEncoder.encode("ABC"),user1.getPassword());
    }

    @Test
    public void saveUser() throws Exception {
        User user = new User("nv011", "nv011");
        userService.saveUser(user);
        User user1 = userService.findOneUser(user.getId());
        assertNotNull(user1);
    }

    @Test
    public void saveTrainer() throws Exception {
        User user = userService.findOneUser(6);
        TrainerInfo trainerInfo = new TrainerInfo();
        trainerInfo.setUser(user);
        userService.saveTrainer(trainerInfo);
        User user1 = userService.findOneUser(6);
        Set<UserProperty> userProperties = user1.getUserProperties();
        assertEquals(5, userProperties.size());
    }

    @Test
    public void setTrainerProperty() throws Exception {
        Set<UserProperty> userProperties = userService.setTrainerProperty(userService.findOneUser(3), "Tai",
                "sdcrono@gmail", "01217366862", "56 Man Thien q9", "External");
        assertEquals(5, userProperties.size());
    }

    @Test
    public void saveTrainee() throws Exception {
        User user = userService.findOneUser(7);
        TraineeInfo traineeInfo = new TraineeInfo();
        traineeInfo.setUser(user);
        userService.saveTrainee(traineeInfo);
        User user1 = userService.findOneUser(7);
        Set<UserProperty> userProperties = user1.getUserProperties();
        assertEquals(8, userProperties.size());
    }

    @Test
    public void setTraineeProperty() throws Exception {
        Set<UserProperty> userProperties = userService.setTraineeProperty(userService.findOneUser(8),
                "Ha", "2017-02-02", "12/12", "F#", "900",
                "2 nam kinh nghiem", "nha rieng", "hcm");
        assertEquals(8,userProperties.size());
    }

    @Test
    public void deleteUser() throws Exception {
        userService.deleteUser(5);
        User user = userService.findOneUser(5);
        assertNull(user);
    }

    @Test
    public void encode() throws Exception {
        String passExpect = userService.encode("ABC");
        assertEquals(passwordEncoder.encode("ABC"), passExpect);
    }

    @Test
    public void getListTraineeCourse() throws Exception {
        List<User> users = userService.getListTraineeCourse(courseService.findOneCourse(2).getId());
        assertEquals(1, users.size());
    }

    @Test
    public void getListTraineeNonCourse() throws Exception {
        List<User> users = userService.getListTraineeNonCourse(courseService.findOneCourse(2).getId());
        assertEquals(2, users.size());
    }

    @Test
    public void getAllUserByRoleAndManager() throws Exception {
        List<User> users = userService.getAllUserByRole(1);
        assertEquals(1, users.size());
    }

    @Test
    public void search() throws Exception {
        List<User> users = userService.search("C++", 4);
        assertEquals(3,users.size());
    }

}