package org.fsoft.tms.service;

import org.fsoft.tms.entity.Category;

import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface CategoryService {

    List<Category> getListCategory();

    void addCategory(Category cat);

    void deleteCategory(int id);

    void updateCategory(Category cat);

    Category findOneCategory(int id);

    List<Category> getListCategoryActive();

    List<Category> searchCategory(String s);
}
