package org.fsoft.tms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.Category;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by DELL on 5/24/2017.
 */
@Controller
@RequestMapping(value = "/tms/courses")
public class CourseController {
    @Autowired
    private CourseService course;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService category;

    @Autowired
    private TopicService topicService;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/")
    public String getPageIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
            model.addAttribute("category", new Category());
            model.addAttribute("listCategory",category.getListCategoryActive());
            if(user.getRole().getId() == 2)
                model.addAttribute("listCourse", course.getAllCourseByStaff(CurrentUser.getInstance().getUser()));
            else
                model.addAttribute("listCourse", course.getAllCourse());
        }


        return "course/index";
    }

    @RequestMapping(value = "/add")
    public String getPageAddCourse(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("listCategory", category.getListCategory());
        return "course/add";
    }

    @RequestMapping(value = "/addCourse")
    public String addCourse(@ModelAttribute Course c) {
        course.addCourse(c);
        return "redirect:/tms/courses/";
    }

    @RequestMapping(value = "/categories")
    public String fillter(@ModelAttribute Category c, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user = loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
            Category cat = category.findOneCategory(c.getId());

            model.addAttribute("category", new Category());
            model.addAttribute("listCategory", category.getListCategoryActive());
            if(user.getRole().getId() == 2){
                List<Course> listCourse = course.getAllCourseByCategoryAndStaff(cat, user);
                model.addAttribute("listCourse", listCourse);
            }
            else
                model.addAttribute("listCourse", course.getAllCourseByCategory(cat));
        }
        return "course/index";
    }

    @RequestMapping(value = "/{id}/trainees/assignment")
    public String getListTrainee(@PathVariable String id, Model model) {
        Course c = course.findOneCourse(Integer.parseInt(id));
        model.addAttribute("course", c);
        model.addAttribute("listTrainee", userService.getListTraineeNonCourse(Integer.parseInt(id)));
        return "course/trainee";
    }

    @RequestMapping(value = "/{courseID}/trainees/{traineeID}/assign")
    public String addTraineeToCourse(@PathVariable("traineeID") String traineeID, @PathVariable("courseID") String courseID, Model model) {
        course.addTrainees(Integer.parseInt(courseID), Integer.parseInt(traineeID));
        return "redirect:/tms/courses/{courseID}/trainees/assignment";
    }

    @RequestMapping(value = "/{courseID}/trainees/{traineeID}/delete")
    public String removeTraineeToCourse(@PathVariable("traineeID") String traineeID, @PathVariable("courseID") String courseID, Model model) {
        course.deleteTrainee(Integer.parseInt(courseID), Integer.parseInt(traineeID));
        return "redirect:/tms/courses/{courseID}/trainees";
    }

    @RequestMapping(value = "/{id}/trainees")
    public String getListTraineeCourse(@PathVariable String id, Model model) {
        Course c = course.findOneCourse(Integer.parseInt(id));
        model.addAttribute("course", c);
        model.addAttribute("listTrainee", userService.getListTraineeCourse(Integer.parseInt(id)));
        return "course/listtrainee";
    }

    @RequestMapping(value = "/{id}/topic")
    public String getListTopicCourse(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user = loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
        }
        Course c = course.findOneCourse(Integer.parseInt(id));
        model.addAttribute("course", c);
        model.addAttribute("listTopic", topicService.findAllTopicByCourse(c));
        return "course/topic";
    }

    @RequestMapping(value = "/{id}/update")
    public String updateCourse(@PathVariable String id, Model model) {
        Course c = course.findOneCourse(Integer.parseInt(id));
        model.addAttribute("course", c);
        model.addAttribute("listCategory", category.getListCategoryActive());
        return "course/update";
    }

    @RequestMapping(value = "/{id}/delete")
    public String deleteCourse(@PathVariable String id, Model model) {
        Course c = course.findOneCourse(Integer.parseInt(id));
        List<Topic> arrTopic = topicService.findAllTopicByCourse(c);
        Set<User> arrTrainee =  c.getTrainees();
        if(arrTopic.size() > 0 || arrTrainee.size() > 0) {
            model.addAttribute("course", c);
            model.addAttribute("listTrainee", arrTrainee);
            model.addAttribute("listTopic", arrTopic);
            return "course/delete";
        }
        else {
            course.deleteCourse(Integer.parseInt(id));
            return "redirect:/tms/courses/";
        }
    }

    @RequestMapping(value = "/{id}/deleteanyway")
    public String deleteAnyway(@PathVariable String id, Model model) {
        Course c = course.findOneCourse(Integer.parseInt(id));
        course.deleteCourse(Integer.parseInt(id));
        return "redirect:/tms/courses/";
    }

    @RequestMapping(value = "/update")
    public String updateCourse(@ModelAttribute Course c) {
        course.updateCourse(c);
        return "redirect:/tms/courses/";
    }

    @RequestMapping(value="/search")
    public String searchCourse(@RequestParam("q") String q, Model model){
        if(q.equals(""))
            return "redirect:/tms/courses/";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null) {
            String name = auth.getName();
            user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
        }
        model.addAttribute("listCourse",course.searchCourse(q, user));
        return "course/index";
    }
}
