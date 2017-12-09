package org.fsoft.tms.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.entity.Category;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.repository.CategoryRepository;
import org.fsoft.tms.service.CategoryService;
import org.fsoft.tms.service.CourseService;
import org.fsoft.tms.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    TopicService topicService;

    @Override
    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category cat) {
        categoryRepository.save(cat);
    }

    @Override
    public void deleteCategory(int id) {
        Category temp = categoryRepository.findOne(id);
        temp.setActive(false);

        List<Course> arrCourse = courseService.getAllCourseByCategory(temp);
        for (Course course: arrCourse) {
            List<Topic> arrTopic = topicService.findAllTopicByCourse(course);
            for (Topic topic: arrTopic) {
                topicService.deleteTopic(topic.getId());
            }
            courseService.deleteCourse(course.getId());
        }
        categoryRepository.save(temp);
    }

    @Override
    public void updateCategory(Category cat) {
        Category temp = categoryRepository.findOne(cat.getId());
        temp.setName(cat.getName());
        temp.setDescription(cat.getDescription());
        temp.setActive(true);
        categoryRepository.save(temp);
    }

    @Override
    public Category findOneCategory(int id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public List<Category> getListCategoryActive() {
        return categoryRepository.findAllByActive(true);
    }

    @Override
    public List<Category> searchCategory(String input) {
        return categoryRepository.findAllByName(input);
    }
}






