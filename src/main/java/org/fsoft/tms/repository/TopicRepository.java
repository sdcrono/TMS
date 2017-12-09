package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by DELL on 5/25/2017.
 */
public interface TopicRepository extends JpaRepository<Topic, Integer>{
    public List<Topic> findAllByTrainer(User user);
    public List<Topic> findAllByCourse_Staff(User user);
    public List<Topic> findAllByCourse(Course course);
    public List<Topic> findAllByActive(Boolean b);

    @Query("Select t from Topic t where upper(t.title) LIKE concat('%',upper(:input),'%') " +
            "or  upper(t.content) LIKE concat('%',upper(:input),'%')")
    public List<Topic> searchTopic(@Param("input") String input);
}
