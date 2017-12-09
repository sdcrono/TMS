package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
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
public class TopicServiceImplTest {

    @Autowired
    private TopicServiceImpl topicService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Test
    public void getAllTopic() throws Exception {
        List<Topic> topics = topicService.getAllTopic();
        assertEquals(4, topics.size());
    }

    @Test
    public void getAllTopicByStaff() throws Exception {
        List<Topic> topics = topicService.getAllTopicByStaff();
        assertEquals(2, topics.size());
    }

    @Test
    public void finOneById() throws Exception {
        Topic topic = topicService.finOneById(1);
        assertNotNull(topic);
    }

    @Test
    public void findAllTopicByTrainer() throws Exception {
        List<Topic> topics = topicService.findAllTopicByTrainer(userService.findOneUser(4));
        assertEquals(2, topics.size());
    }

    @Test
    public void findAllTopicByCourse() throws Exception {
        List<Topic> topics = topicService.findAllTopicByCourse(userService.findOneUser(2), courseService.findOneCourse(2));
        assertEquals(1, topics.size());
    }

    @Test
    public void findAllCourseOfUser() throws Exception {
        List<Course> courses = topicService.findAllCourseOfUser(userService.findOneUser(4));
        assertEquals(2, courses.size());
    }
    @Test
    public void updateTopic() throws Exception {
        Topic topic = topicService.finOneById(1);
        topic.setTitle("Korean Basics");
        topicService.updateTopic(topic);
        Topic topic1 = topicService.finOneById(1);
        assertEquals("Korean Basics", topic1.getTitle());
    }

    @Test
    public void deleteTopic() throws Exception {
        topicService.deleteTopic(2);
        Topic topic = topicService.finOneById(2);
        assertNull(topic);
    }

    @Test
    public void addTopic() throws Exception {
        Topic topic = new Topic();
        topic.setTitle("SQL query basic");
        topic.setContent("ABC");
        topicService.addTopic(topic);
        Topic topic1 = topicService.finOneById(topic.getId());
        assertEquals("SQL query basic", topic1.getTitle());
    }

    @Test
    public void addTrainerToTopic() throws Exception {
        topicService.addTrainerToTopic(topicService.finOneById(2).getId(), userService.findOneUser(3).getId());
        Topic topic = topicService.finOneById(2);
        User user = topic.getTrainer();
        assertEquals("gv001", user.getUsername());
    }

    @Test
    public void searchTopic() throws Exception {
        List<Topic> topics = topicService.searchTopic("ABC", userService.findOneUser(3));
        assertEquals(1, topics.size());
    }

}