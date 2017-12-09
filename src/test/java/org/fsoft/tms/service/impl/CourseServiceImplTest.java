package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceImplTest {

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getAllCourse() throws Exception {
        List<Course> courses = courseService.getAllCourse();
        assertEquals(4, courses.size());
    }

    @Test
    public void getAllCourseByStaff() throws Exception {
        List<Course> courses = courseService.getAllCourseByStaff(userService.findOneUser(2));
        assertEquals(2, courses.size());
    }

    @Test
    public void addCourse() throws Exception {
        Course course = new Course();
        course.setName("English 7");
        course.setDescription("Tieng anh 7");
        course.setCategory(categoryService.findOneCategory(3));
        assertNull(course);
        courseService.addCourse(course);
        Course courseAdd = courseService.findOneCourse(course.getId());
        assertNotNull(courseAdd);
    }

    @Test
    public void findOneCourse() throws Exception {
        Course course = courseService.findOneCourse(1);
        assertNotNull(course);
    }

    @Test
    public void updateCourse() throws Exception {
        Course course = courseService.findOneCourse(3);
        course.setDescription("ABC");
        courseService.updateCourse(course);
        Course courseUpdated = courseService.findOneCourse(course.getId());
        assertEquals("ABC",courseUpdated.getDescription());
    }

    @Test
    public void addTrainees() throws Exception {
        Course course = courseService.findOneCourse(3);
        User user = userService.findOneUser(4);
        courseService.addTrainees(course.getId(),user.getId());
        Set<User> trainees = courseService.findOneCourse(3).getTrainees();
        assertEquals(1, trainees.size());
    }

    @Test
    public void deleteCourse() throws Exception {
        courseService.deleteCourse(3);
        Course course = courseService.findOneCourse(3);
        assertNull(course);
    }

    @Test
    public void getAllCourseByCategory() throws Exception {
        List<Course> courses = courseService.getAllCourseByCategory(categoryService.findOneCategory(1));
        assertEquals(2,courses.size());
    }

    @Test
    public void searchCourse() throws Exception {
        List<Course> courses = courseService.searchCourse("ABC", userService.findOneUser(2));
        assertEquals(1, courses.size());
    }

}