package org.fsoft.tms.service;

import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;

import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface TopicService {

    List<Topic> getAllTopic();

    List<Topic> getAllTopicByStaff();

    List<Topic> getAllTopicByActive(Boolean b);

    Topic finOneById(Integer id);

    List<Topic> findAllTopicByTrainer(User user);

    List<Topic> findAllTopicByCourse(User user, Course course);

    List<Topic> findAllTopicByCourse(Course course);

    List<Course> findAllCourseOfUser(User user);

    void updateTopic(Topic topic);

    void deleteTopic(int id);

    void addTopic(Topic t);

    void addTrainerToTopic(int topicID, int trainerID);

    List<Topic> searchTopic(String input, User user);

    void unAssignTopicToTrainer(User user);
}

