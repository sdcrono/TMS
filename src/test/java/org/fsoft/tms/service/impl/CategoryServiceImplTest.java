package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void getListCategory() throws Exception {
        List<Category> categories = categoryService.getListCategory();
        assertEquals(2, categories.size());
    }

    @Test
    public void addCategory() throws Exception {
        Category category = new Category("Korean", "Tieng Han");
        assertNull(category);
        categoryService.addCategory(category);
        Category actualCategory = categoryService.findOneCategory(category.getId());
        assertNotNull(actualCategory);
    }

    @Test
    public void deleteCategory() throws Exception {
        Category category  = categoryService.findOneCategory(3);
        categoryService.deleteCategory(3);
        assertNull(categoryService.findOneCategory(3));
    }

    @Test
    public void updateCategory() throws Exception {
        Category category  = categoryService.findOneCategory(3);
        category.setName("ABC");
        categoryService.updateCategory(category);
        Category categoryUpdated  = categoryService.findOneCategory(3);
        assertEquals("ABC",categoryUpdated.getName());
    }

    @Test
    public void findOneCategory() throws Exception {
        Category category = categoryService.findOneCategory(3);
        assertNotNull(category);
    }

    @Test
    public void getListCategoryActive() throws Exception {
        List<Category> categories = categoryService.getListCategoryActive();
        assertEquals(2, categories.size());
    }

    @Test
    public void searchCategory() throws Exception {
        List<Category> categories = categoryService.searchCategory("ABC");
        assertEquals(2, categories.size());
    }

}