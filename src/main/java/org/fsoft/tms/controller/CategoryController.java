package org.fsoft.tms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.entity.Category;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.service.CategoryService;
import org.fsoft.tms.service.CourseService;
import org.fsoft.tms.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by DELL on 5/23/2017.
 */
@Controller
@RequestMapping(value = "/tms/categories")
public class CategoryController {

    private final Logger logger = LogManager.getLogger();
    @Autowired
    private CategoryService category;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/")
    public String getAllCategory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
        }
        model.addAttribute("listCategory", category.getListCategory());
        return "category/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model) {
        if (q.equals("")) {
            return "redirect:/tms/categories/";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
        }
        model.addAttribute("listCategory", category.searchCategory(q));
        return "category/index";
    }

    @RequestMapping(value = "/add")
    public String getPageAddCategory( Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @RequestMapping(value = "/addCategory")
    public String addCategory(@ModelAttribute Category cat) {
        cat.setActive(true);
        category.addCategory(cat);
        return "redirect:/tms/categories/";
    }

    @RequestMapping(value = "/{id}/delete")
    public String deleteCatogory(@PathVariable String id, Model model) {
        Category temp = category.findOneCategory(Integer.parseInt(id));
        List<Course> arrCourse = courseService.getAllCourseByCategory(temp);
        if(arrCourse.size() > 0) {
            model.addAttribute("category", temp);
            model.addAttribute("listCourse", arrCourse);
            return "category/coursedelete";
        }
        else
            category.deleteCategory(Integer.parseInt(id));
        return "redirect:/tms/categories/";
    }

    @RequestMapping(value = "/{id}/deleteanyway")
    public String deleteAnyway(@PathVariable String id) {
        category.deleteCategory(Integer.parseInt(id));
        return "redirect:/tms/categories/";
    }

    @RequestMapping(value = "/{id}/courses")
    public String listCourse(@PathVariable String id, Model model) {
        Category cat = category.findOneCategory(Integer.parseInt(id));
        List<Course> arrCourse = courseService.getAllCourseByCategory(cat);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
        }
        model.addAttribute("listCourse", arrCourse);
        return "category/course";
    }

    @RequestMapping(value = "/{id}/update")
    public String updateCategory(@PathVariable String id, Model model) {
        Category cat = category.findOneCategory(Integer.parseInt(id));
        model.addAttribute("category", cat);
        return "category/update";
    }

    @RequestMapping(value = "/update")
    public String updateCatogory(@ModelAttribute Category cat) {
        category.updateCategory(cat);
        return "redirect:/tms/categories/";
    }

}
