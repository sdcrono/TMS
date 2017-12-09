package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Category;
import org.fsoft.tms.entity.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 1-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest//(classes = {RepositoryConfiguration.class})
public class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllByTrainer() throws Exception {
        List<Topic> topics = topicRepository.findAllByTrainer(userRepository.getOne(59));
        assertEquals(1,topics.size());
    }

    @Test
    public void findAllByCourse_Staff() throws Exception {
        List<Topic> topics = topicRepository.findAllByCourse_Staff(userRepository.getOne(57));
        assertEquals(2, topics.size());
    }

    @Test
    public void searchTopic() throws Exception {
        List<Topic> topics = topicRepository.searchTopic("English");
        assertEquals(2, topics.size());
    }

}