package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Category;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by thehaohcm on 5/19/17.
 */

public interface CourseRepository extends JpaRepository<Course, Integer>{
    public Set<Course> findAllById(Integer id);
    public List<Course> findAllByCategory(Category c);
    public List<Course> findAllByCategoryAndStaff(Category c, User u);
    public List<Course> findAllByStaff(User user);
    public List<Course> findAllByActive(Boolean b);

    @Query("Select c from Course c where upper(c.name) LIKE concat('%',upper(:input),'%') " +
            "or  upper(c.description) LIKE concat('%',upper(:input),'%')"+
            "or upper(FUNCTION('TO_CHAR',c.createdDate)) like concat('%',upper(:input),'%') ")
    public List<Course> searchCourse(@Param("input") String input);
}
