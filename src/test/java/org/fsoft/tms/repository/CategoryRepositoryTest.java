package org.fsoft.tms.repository;

import org.fsoft.tms.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 28-May-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest//(classes = {RepositoryConfiguration.class})
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSaveCategory(){

        Category category = new Category();
        category.setName("English");
        category.setDescription("Learning English");
        category.setActive(true);
        assertNull(category.getId());
        categoryRepository.save(category);
        assertNotNull(category.getId());

        Category fetchedCategory = categoryRepository.findOne(category.getId());
        assertNotNull(fetchedCategory);
        assertEquals(category.getId(), fetchedCategory.getId());
        assertEquals(category.getName(), fetchedCategory.getName());
        assertEquals(category.getDescription(), fetchedCategory.getDescription());

        fetchedCategory.setDescription("Learn English");
        categoryRepository.save(fetchedCategory);
        Category fetchedUpdatedCategory = categoryRepository.findOne(fetchedCategory.getId());
        assertEquals(fetchedCategory.getDescription(), fetchedUpdatedCategory.getDescription());

        long categoryCount = categoryRepository.count();
        assertEquals(categoryCount, 4);

        Iterable<Category> categories = categoryRepository.findAll();
        int count = 0;
        for(Category c : categories){
            count++;
        }
        assertEquals(count, 4);

        fetchedUpdatedCategory.setActive(false);
        categoryRepository.save(fetchedUpdatedCategory);
        Category fetchedDeletedCategory = categoryRepository.findOne(fetchedUpdatedCategory.getId());
        assertEquals(fetchedDeletedCategory.getActive(), false);

    }

}