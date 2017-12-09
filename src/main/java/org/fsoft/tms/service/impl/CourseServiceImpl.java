
package org.fsoft.tms.service.impl;
import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.Category;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.repository.CourseRepository;
import org.fsoft.tms.repository.TopicRepository;
import org.fsoft.tms.repository.UserRepository;
import org.fsoft.tms.service.CourseService;
import org.fsoft.tms.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Isabella on 29-May-2017.
 */
@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TopicService topicService;

    @Override
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getAllCourseByStaff(User user) {
        return courseRepository.findAllByStaff(user);
    }

    @Override
    public List<Course> getAllCourseByCategoryAndStaff(Category c, User user) {
        return courseRepository.findAllByCategoryAndStaff(c, user);
    }

    @Override
    public void addCourse(Course course) {
        course.setActive(true);
        course.setStaff(CurrentUser.getInstance().getUser());
        courseRepository.save(course);
    }

    @Override
    public Course findOneCourse(int id) {
        return courseRepository.findOne(id);
    }

    @Override
    public void updateCourse(Course c) {
        Course temp = courseRepository.findOne(c.getId());
        temp.setName(c.getName());
        temp.setDescription(c.getDescription());
        temp.setCategory(c.getCategory());
        courseRepository.save(temp);
    }

    @Override
    public void addTrainees(int courseID, int traineeID) {
        Course temp = courseRepository.findOne(courseID);
        Set<User> arr = temp.getTrainees();
        arr.add(userRepository.findAllById(traineeID));
        courseRepository.save(temp);
    }

    @Override
    public void deleteTrainee(int courseID, int traineeID) {
        Course temp = courseRepository.findOne(courseID);
        Set<User> arr = temp.getTrainees();
        arr.remove(userRepository.findAllById(traineeID));
        courseRepository.save(temp);
    }

    @Override
    public void deleteCourse(int id) {
        Course c = courseRepository.findOne(id);
        c.setActive(false);
        List<Topic> arrTopic = topicService.findAllTopicByCourse(c);
        for (Topic topic: arrTopic) {
            topicService.deleteTopic(topic.getId());
        }
        c.getTrainees().clear();
        courseRepository.save(c);
    }

    @Override
    public List<Course> getAllCourseByCategory(Category c) {
        return  courseRepository.findAllByCategory(c);
    }

    @Override
    public List<Course> searchCourse(String input, User u){
        List<Course> courses = courseRepository.searchCourse(input);
        List<Course> courses1 = new ArrayList<>();
        if(!u.getRole().getName().equals("ROLE_ADMIN")){
            for(int i = 0; i < courses.size(); i++) {
                if(courses.get(i).getStaff().getId()== u.getId())
                    courses1.add(courses.get(i));
            }
            return courses1;
        }
        else
            return courses;
    }

    @Override
    public void changeManager(int userIdOld, int userIdNew) {
        User u = userRepository.findOne(userIdOld);
        List<Course> listCourse = getAllCourseByStaff(u);
        for (Course c : listCourse) {
            Course temp = courseRepository.findOne(c.getId());
            temp.setStaff(userRepository.findOne(userIdNew));
            courseRepository.save(temp);
        }
        u.getCourses().clear();
        userRepository.save(u);
    }

    @Override
    public List<Course> getAllCourseByActive(Boolean b) {
        return courseRepository.findAllByActive(b);
    }
}
