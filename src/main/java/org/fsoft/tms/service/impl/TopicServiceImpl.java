package org.fsoft.tms.service.impl;

import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.repository.TopicRepository;
import org.fsoft.tms.repository.UserRepository;
import org.fsoft.tms.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    TopicRepository topic;

    @Autowired
    UserRepository userRepository;

    private final static int PAGESIZE = 4;

    @Override
    public List<Topic> getAllTopic() {
//        PageRequest request = new PageRequest(2, PAGESIZE, Sort.Direction.ASC, "id");
//        return topic.findAll(request).getContent();
        return  topic.findAll();
    }

    @Override
    public List<Topic> getAllTopicByStaff() {
        return topic.findAllByCourse_Staff(CurrentUser.getInstance().getUser());
    }

    @Override
    public Topic finOneById(Integer id) {
        return topic.findOne(id);
    }

    @Override
    public List<Topic> findAllTopicByCourse(Course course) {
        return topic.findAllByCourse(course);
    }

    @Override
    public List<Topic> findAllTopicByTrainer(User user) {
        return topic.findAllByTrainer(user);
    }

    @Override
    public List<Topic> findAllTopicByCourse(User user, Course course) {
        List<Topic> arrTopic = topic.findAllByTrainer(user);
        List<Topic> arrTopics = new ArrayList<>();
        for (Topic tp: arrTopic) {
            if (tp.getCourse().getName().equals(course.getName())) {
                arrTopics.add(tp);
            }
        }
        return arrTopics;
    }

    @Override
    public List<Course> findAllCourseOfUser(User user) {
        List<Topic> arrTopic = topic.findAllByTrainer(user);
        List<Course> arrCourses = new ArrayList<>();
        int j = 0;
        if (arrTopic.size() > 0) {
            arrCourses.add(arrTopic.get(0).getCourse());
            for (int i = 1; i < arrTopic.size(); i++) {
                j = 0;
                for (Course c:arrCourses) {
                    if(arrTopic.get(i).getCourse().equals(c))
                        j++;
                }
                if(j == 0)
                    arrCourses.add(arrTopic.get(i).getCourse());
            }
        }
        return arrCourses;
    }

    @Override
    public void updateTopic(Topic t) {
        Topic temp = topic.findOne(t.getId());
        temp.setTitle(t.getTitle());
        temp.setContent(t.getContent());
        topic.save(temp);
    }

    @Override
    public void deleteTopic(int id) {
        Topic temp = topic.findOne(id);
        temp.setActive(false);
        temp.setTrainer(null);
        topic.save(temp);
    }

    @Override
    public void addTopic(Topic t) {
        t.setActive(true);
        topic.save(t);
    }

    @Override
    public void addTrainerToTopic(int topicID, int trainerID) {
        Topic t = topic.findOne(topicID);
        User u = userRepository.findOne(trainerID);
        t.setTrainer(u);
        topic.save(t);
    }

    @Override
    public List<Topic> searchTopic(String input,User user){
        List<Topic> topics = topic.searchTopic(input);
        List<Topic> topics1 = new ArrayList<>();
        if(!user.getRole().getName().equals("ROLE_ADMIN")){
            for (Topic t: topics) {
                if(t.getCourse().getStaff().getId() == user.getId())
                    topics1.add(t);
            }
            return topics1;
        }
        else
            return topics;
    }

    @Override
    public void unAssignTopicToTrainer(User user) {
        List<Topic> arrTopic = findAllTopicByTrainer(user);
        for (Topic t : arrTopic) {
            Topic tp = topic.findOne(t.getId());
            tp.setTrainer(null);
            topic.save(tp);
        }
        User u = userRepository.findOne(user.getId());
        u.getTopics().clear();
        userRepository.save(u);
    }

    @Override
    public List<Topic> getAllTopicByActive(Boolean b) {
        return topic.findAllByActive(b);
    }
}
