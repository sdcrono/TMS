
package org.fsoft.tms.service;

import org.fsoft.tms.entity.*;
import org.fsoft.tms.entity.Course;

import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface CourseService {

    List<Course> getAllCourse();

    List<Course> getAllCourseByActive(Boolean b);

    List<Course> getAllCourseByStaff(User user);

    List<Course> getAllCourseByCategoryAndStaff(Category c, User user);

    List<Course> getAllCourseByCategory(Category c);

    void addCourse(Course course);

    Course findOneCourse(int id);

    void updateCourse(Course c);

    void addTrainees(int courseID, int traineeID);

    void deleteTrainee(int courseID, int traineeID);

    void deleteCourse(int id);

    List<Course> searchCourse(String input, User user);

    void changeManager(int userIdOld, int userIdNew);
}