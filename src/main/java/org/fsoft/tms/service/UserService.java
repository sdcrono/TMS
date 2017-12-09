package org.fsoft.tms.service;

import org.fsoft.tms.entity.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface UserService {

    List<User> getAllUser();

    List<User> getAllUserByRole(int roleID);

    List<User> getAllUserByRoleAndManager(int roleID, int managerID);

    List<User> getListTraineeCourse(int courseID);

    List<User> getListTraineeNonCourse(int courseID);

    void addUser(User u, int roleId, int managerID);

    void addTrainee(User u, int staffId);

    User findOneUser(int id);

    void updateUser(User c);

    void updateUser(User c,boolean changePassword);

    void addPropertyForUser();

    void addRole();

    void addManager();

    String encode(String password);

    void addCourse();

    void addTopic();

    void saveUser(User user);

    void deleteUser(int id);

    void recoverUser(int id);

    void saveTrainer(TrainerInfo trainerInfo);

    Set<UserProperty> setTrainerProperty(User user, String name, String email, String phone, String address, String type);

    void saveTrainee(TraineeInfo traineeInfo);

    List<User> search(String value, int roleID);

    List<User> search(String value, int roleID, User staff);

    Set<UserProperty> setTraineeProperty(User user,String name,String birthDate,String education,
                                         String programmingLanguage,String toeicScore,String experimentDetail,
                                         String department,String location);

    boolean checkUsername(String username);

    void unAssignTraineeToCourse(int userID);

    void changeManagerTrainee(int userIdOld, int userIdNew);

    void changeManagerTrainer(int userIdOld, int userIdNew);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);

    VerificationToken generateNewVerificationToken(final String existingVerificationToken);
}
