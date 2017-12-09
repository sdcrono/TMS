package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by thehaohcm on 5/19/17.
 */
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public List<Category> findAllByActive(Boolean b);

    @Query("Select c from Category c where upper(c.name) LIKE concat('%',upper(:input),'%') or  upper(c.description) LIKE concat('%',upper(:input),'%')")
    List<Category> findAllByName(@Param("input") String input);
}

