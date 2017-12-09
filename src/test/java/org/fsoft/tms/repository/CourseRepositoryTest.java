package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 28-May-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest//(classes = {RepositoryConfiguration.class})
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveCourse(){

        Course course = new Course();
        course.setName("English 1");
        course.setDescription("Learning English Unit 1");
        course.setCategory(categoryRepository.findOne(1));
        assertNull(course.getId());
        courseRepository.save(course);
        assertNotNull(course.getId());

        Course fetchedCourse = courseRepository.findOne(course.getId());
        assertNotNull(fetchedCourse);
        assertEquals(course.getId(), fetchedCourse.getId());
        assertEquals(course.getName(), fetchedCourse.getName());
        assertEquals(course.getDescription(), fetchedCourse.getDescription());

        fetchedCourse.setDescription("Learn English Unit 0");
        courseRepository.save(fetchedCourse);
        Course fetchedUpdatedCourse = courseRepository.findOne(fetchedCourse.getId());
        assertEquals(fetchedCourse.getDescription(), fetchedUpdatedCourse.getDescription());

        long courseCount = courseRepository.count();
        assertEquals(courseCount, 2);

        Iterable<Course> courses = courseRepository.findAll();
        int count = 0;
        for(Course c : courses){
            count++;
        }
        assertEquals(count, 2);

        fetchedUpdatedCourse.setActive(false);
        courseRepository.save(fetchedUpdatedCourse);
        Course fetchedDeletedCourse = courseRepository.findOne(fetchedCourse.getId());
        assertEquals(fetchedDeletedCourse.getActive(), false);

    }

    @Test
    public void findAllById() throws Exception {
        Set<Course> categories = courseRepository.findAllById(1);
        int count = 0;
        for(Course c : categories){
            count++;
        }
        assertEquals(count, 1);
    }

}