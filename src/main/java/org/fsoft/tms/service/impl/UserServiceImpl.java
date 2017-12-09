package org.fsoft.tms.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.*;
import org.fsoft.tms.repository.*;
import org.fsoft.tms.service.TopicService;
import org.fsoft.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * Created by thehaohcm on 5/30/17.
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger();

    private static final int EXPIRATION = 1;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserPropertyRepository userPropertyRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUserByRole(int roleID) {
        Role role = roleRepository.findOne(roleID);
        return userRepository.findAllByRole(role);
    }

    @Override
    public void addUser(User u, int roleId, int managerID) {
        Role role = roleRepository.findOne(roleId);
        u.setRole(role);
        u.setActive(true);
        User manager = userRepository.findOne(managerID);
        u.setManager(manager);
        String password = u.getPassword();
        u.setPassword(encode(password));
        userRepository.save(u);
    }

    @Override
    public void addTrainee(User u, int staffId) {
        Role role = roleRepository.findOne(4);
        u.setRole(role);
        u.setActive(true);
        User manager = userRepository.findOne(staffId);
        u.setManager(manager);
        String password = u.getPassword();
        u.setPassword(encode(password));
        userRepository.save(u);
    }

    @Override
    public User findOneUser(int id) {
        return userRepository.findOne(id);
    }

    @Override
    public void updateUser(User c) {
        User temp = userRepository.findOne(c.getId());
        logger.debug("temp1:" + temp.getPassword() + ":a");
        logger.debug("pass:" + c.getPassword() + ":a");
        if (!(c.getPassword().equals(""))) {
            temp.setPassword(encode(c.getPassword()));
            logger.debug("pass1:" + temp.getPassword() + ":a");
        }
        temp.setUsername(c.getUsername());
        logger.debug("temp:" + temp.getPassword() + ":a");
        userRepository.save(temp);
    }

    public void updateUser(User c, boolean changePassword) {
        User temp = userRepository.findOne(c.getId());
        logger.debug("temp1:" + temp.getPassword() + ":a");
        logger.debug("pass:" + c.getPassword() + ":a");
        if (changePassword) {
            temp.setPassword(encode(c.getPassword()));
            logger.debug("pass1:" + temp.getPassword() + ":a");
        }
        temp.setUsername(c.getUsername());
        logger.debug("temp:" + temp.getPassword() + ":a");
        userRepository.save(temp);
    }

    @Override
    public void addPropertyForUser() {
        User temp = new User();
        temp.setActive(true);
        temp.setPassword("a1");
        temp.setUsername("a1");

        Property property = new Property();
        property.setDescription("adf1");
        property.setName("ad1s");
        propertyRepository.save(property);

        UserProperty userProperty = new UserProperty();
        userProperty.setUser(temp);
        userProperty.setProperty(property);
        userProperty.setValue("Tran Manh Cam");

        temp.getUserProperties().add(userProperty);
        userRepository.save(temp);

    }

    @Override
    public void addRole() {
        User temp = new User();
        temp.setActive(true);
        temp.setPassword("a2");
        temp.setUsername("a2");

        Role role = roleRepository.findOne(2);
        temp.setRole(role);
        userRepository.save(temp);
    }

    @Override
    public void addManager() {
        User temp1 = new User();
        temp1.setActive(true);
        temp1.setPassword("a2");
        temp1.setUsername("a2");

        Role role1 = roleRepository.findOne(2);
        temp1.setRole(role1);
        temp1.setManager(null);
        userRepository.save(temp1);

        User temp = new User();
        temp.setActive(false);
        temp.setPassword("a23");
        temp.setUsername("a23");

        Role role = roleRepository.findOne(2);
        temp.setRole(role);

        temp.setManager(userRepository.findOne(1));
        userRepository.save(temp);
    }

    @Override
    public void addCourse() {
        User temp1 = userRepository.findOne(2);
        temp1.setTraineeCourses(courseRepository.findAllById(3));
        userRepository.save(temp1);
    }

    @Override
    public void addTopic() {
        User temp1 = userRepository.findOne(7);
        Course tempCourse = courseRepository.findOne(2);
        Topic topic = new Topic();
        topic.setTrainer(temp1);
        topic.setCourse(tempCourse);
        topic.setTitle("logic");
        topic.setContent("tri tue");
        topicRepository.save(topic);
    }

    @Override
    public void saveUser(User user) {
        User user1 = userRepository.findOne(user.getId());
        user1.setUserProperties(user.getUserProperties());
        userRepository.save(user1);
    }

    @Override
    public void saveTrainer(TrainerInfo trainerInfo) {
        Set<UserProperty> userProperties = new HashSet<>(0);
        userProperties = setTrainerProperty(trainerInfo.getUser(), trainerInfo.getName(),
                trainerInfo.getEmail(), trainerInfo.getPhone(), trainerInfo.getAddress(), trainerInfo.getType());

        User user = userRepository.findOne(trainerInfo.getUser().getId());
        user.setUserProperties(userProperties);
        userRepository.save(user);
    }

    @Override
    public Set<UserProperty> setTrainerProperty(User user, String name, String email, String phone, String address, String type) {
        UserProperty userProperty = new UserProperty();
        Set<UserProperty> userProperties = new HashSet<>(0);
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(1));
        userProperty.setValue(name);
        userProperties.add(userProperty);
        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(10));
        userProperty.setValue(email);
        userProperties.add(userProperty);
        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(9));
        userProperty.setValue(phone);
        userProperties.add(userProperty);
        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(8));
        userProperty.setValue(address);
        userProperties.add(userProperty);
        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(11));
        userProperty.setValue(type);
        userProperties.add(userProperty);
        return userProperties;
    }

    public void saveTrainee(TraineeInfo trainee) {
        Set<UserProperty> userProperties = new HashSet<>(0);
        userProperties = setTraineeProperty(trainee.getUser(), trainee.getName(), trainee.getBirthDate(),
                trainee.getEducation(), trainee.getProgrammingLanguage(), trainee.getToeicScore(),
                trainee.getExperienceDetail(), trainee.getDepartment(), trainee.getLocation());

        User user = userRepository.findOne(trainee.getUser().getId());
        user.setUserProperties(userProperties);
        userRepository.save(user);
    }

    public Set<UserProperty> setTraineeProperty(User user, String name, String birthDate, String education,
                                                String programmingLanguage, String toeicScrore, String experienceDetail,
                                                String department, String localtion) {
        Set<UserProperty> userProperties = new HashSet<>(0);
        UserProperty userProperty;

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(1));
        userProperty.setValue(name);
        userProperties.add(userProperty);

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(2));
        userProperty.setValue(birthDate);
        userProperties.add(userProperty);

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(3));
        userProperty.setValue(education);
        userProperties.add(userProperty);

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(4));
        userProperty.setValue(programmingLanguage);
        userProperties.add(userProperty);

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(5));
        userProperty.setValue(toeicScrore);
        userProperties.add(userProperty);

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(6));
        userProperty.setValue(experienceDetail);
        userProperties.add(userProperty);

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(7));
        userProperty.setValue(department);
        userProperties.add(userProperty);

        userProperty = new UserProperty();
        userProperty.setUser(user);
        userProperty.setProperty(propertyRepository.findOne(12));
        userProperty.setValue(localtion);
        userProperties.add(userProperty);

        return userProperties;
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findOne(id);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void recoverUser(int id) {
        User user = userRepository.findOne(id);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public List<User> getListTraineeCourse(int courseID) {
        Course course = courseRepository.findOne(courseID);
        List<User> arr = getAllUserByRoleAndManager(4, CurrentUser.getInstance().getUser().getId());
        List<User> arrUserCourse = new ArrayList<>();
        for (User user : arr) {
            if (course.getTrainees().contains(user))
                arrUserCourse.add(user);
        }
        return arrUserCourse;
    }

    @Override

    public List<User> getListTraineeNonCourse(int courseID) {
        Course course = courseRepository.findOne(courseID);
        List<User> arr = getAllUserByRoleAndManager(4, CurrentUser.getInstance().getUser().getId());
        List<User> arrUserCourse = new ArrayList<>();
        for (User user : arr) {
            if (!course.getTrainees().contains(user))
                arrUserCourse.add(user);
        }
        return arrUserCourse;
    }

    @Override
    public List<User> getAllUserByRoleAndManager(int roleID, int staffID) {
        Role role = roleRepository.findOne(roleID);
        User user = userRepository.findOne(staffID);
        return userRepository.findAllByRoleAndManager(role, user);
    }

    @Override
    public List<User> search(String value, int roleID) {
        List<UserProperty> arr = userPropertyRepository.getUserProperties(value);
        int size = arr.size();
        List<User> arr_temp = new ArrayList<>();
        if (size > 0) {
            arr_temp.add(arr.get(0).getUser());
            for (int i = 1; i < arr.size(); i++) {
                int z = 0;
                for (int j = 0; j < arr_temp.size(); j++) {
                    if (arr.get(i).getUser().getId() != arr_temp.get(j).getId())
                        z++;
                    else
                        break;
                }
                if (z == arr_temp.size())
                    arr_temp.add(arr.get(i).getUser());
            }
        }

        List<User> trainee = new ArrayList<>();
        for (User u : arr_temp) {
            if (u.getRole().getId() == roleID)
                trainee.add(u);

        }
        return trainee;
    }

    @Override
    public List<User> search(String value, int roleID, User manager) {
        List<UserProperty> arr = userPropertyRepository.getUserProperties(value);
        int size = arr.size();
        List<User> arr_temp = new ArrayList<>();
        if (size > 0) {
            arr_temp.add(arr.get(0).getUser());
            for (int i = 1; i < arr.size(); i++) {
                int z = 0;
                for (int j = 0; j < arr_temp.size(); j++) {
                    if (arr.get(i).getUser().getId() != arr_temp.get(j).getId())
                        z++;
                    else
                        break;
                }
                if (z == arr_temp.size())
                    arr_temp.add(arr.get(i).getUser());
            }
        }

        List<User> trainee = new ArrayList<>();
        if(manager.getRole().getName().equals("ROLE_ADMIN")){
            for (User u : arr_temp) {
                if (u.getRole().getId() == roleID)
                    trainee.add(u);
            }
            return trainee;
        }else {
            for (User u : arr_temp) {
                if (u.getRole().getId() == roleID && u.getManager().getId() == manager.getId())
                    trainee.add(u);
            }
            return trainee;
        }
    }

    @Override
    public boolean checkUsername(String username){
        int count=userRepository.countByUsername(username);
        logger.debug("count: "+count);
        if(count>0)
            return false;
        return true;
    }

    @Override
    public void unAssignTraineeToCourse(int id) {
        User user  = findOneUser(id);
        user.getTraineeCourses().clear();
    }

    @Override
    public void changeManagerTrainee(int userIdOld, int userIdNew) {
        List<User> listTrainee = getAllUserByRoleAndManager(4, userIdOld);
        for (User u: listTrainee) {
            User temp = findOneUser(u.getId());
            temp.setManager(findOneUser(userIdNew));
            userRepository.save(temp);
        }
        User temp = findOneUser(userIdOld);
        temp.getUsers().clear();
        userRepository.save(temp);
    }

    @Override
    public void changeManagerTrainer(int userIdOld, int userIdNew) {
        List<User> listTrainer = getAllUserByRoleAndManager(3, userIdOld);
        for (User u: listTrainer) {
            User temp = findOneUser(u.getId());
            temp.setManager(findOneUser(userIdNew));
            userRepository.save(temp);
        }
        User temp = findOneUser(userIdOld);
        temp.getUsers().clear();
        userRepository.save(temp);
    }

    @Override
    public void createVerificationToken(User user, String token){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        Date date=new Date(cal.getTime().getTime());
//        logger.debug("ngay het han:"+date.toString());
        VerificationToken myToken = new VerificationToken(token, user, date);
        myToken.setExpired(false);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        Date date=new Date(cal.getTime().getTime());
        VerificationToken verificationToken1=verificationTokenRepository.findByToken(verificationToken);
//        logger.debug("het han:"+verificationToken1.getExpiryDate().toString()+",hien tai:"+date.toString());
        if(verificationToken1.getExpiryDate().before(date)){
            verificationToken1.setExpired(true);
        }
        return verificationToken1;
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken){
        VerificationToken verificationToken=verificationTokenRepository.findByToken(existingVerificationToken);
        verificationToken.updateToken(UUID.randomUUID().toString());
        verificationToken=verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

//    public void deleteVerificationToken(final String existingVerificationToken){
//        VerificationToken verificationToken=verificationTokenRepository.findByToken(existingVerificationToken);
//        verificationTokenRepository.delete(verificationToken);
//    }
}
