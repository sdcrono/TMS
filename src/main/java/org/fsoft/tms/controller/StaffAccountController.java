package org.fsoft.tms.controller;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.service.CourseService;
import org.fsoft.tms.service.UserService;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by DELL on 5/31/2017.
 */
@Controller
@RequestMapping(value = "/tms/staffs")
public class StaffAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/")
    public String getPageIndex(Model model) {
        model.addAttribute("listUser", userService.getAllUserByRole(2));
        return "staffaccount/index";
    }

    @RequestMapping(value = "/add")
    public String getPageAdd(Model model) {
        model.addAttribute("user", new User());
        return "staffaccount/add";
    }

    @RequestMapping(value = "/addAccount")
    public String addAccount (Model model,@ModelAttribute  User user) {
        if(!userService.checkUsername(user.getUsername())){
            model.addAttribute("user",user);
            model.addAttribute("error","Username is exited!");
            return "staffaccount/add";
        }
        userService.addUser(user, 2,1);
        return "redirect:/tms/staffs/";
    }

    @RequestMapping(value = "/{id}/update")
    public String getPageUpdate(@PathVariable String id, Model model) {
        User user = userService.findOneUser(Integer.parseInt(id));
        User user_temp=new User();
        user_temp.setId(user.getId());
        user_temp.setUsername(user.getUsername());
        user_temp.setPassword(user.getPassword());
        model.addAttribute("user", user);
        return "staffaccount/update";
    }

    @RequestMapping(value = "/update")
    public String updateAccount (@ModelAttribute User user) {
        User user1=userService.findOneUser(user.getId());
        String encrypt_pass= userService.encode(user.getPassword());
        if(!user.getPassword().trim().equals("")&&!user1.getPassword().equals(encrypt_pass))
            userService.updateUser(user,true);
        userService.updateUser(user,false);
        return "redirect:/tms/staffs/";
    }

    @RequestMapping(value = "/{id}/delete")
    public String deleteAccount(@PathVariable String id, Model model) {
        User user = userService.findOneUser(Integer.parseInt(id));
        List<User> listTrainee = userService.getAllUserByRoleAndManager(4, user.getId());
        List<User> listTrainer = userService.getAllUserByRoleAndManager(3, user.getId());
        List<Course> listCourse = courseService.getAllCourseByStaff(user);
        if(listCourse.size() > 0 || listTrainee.size() > 0 || listTrainer.size() > 0) {
            model.addAttribute("listTrainee", listTrainee);
            model.addAttribute("listTrainer", listTrainer);
            model.addAttribute("listCourse", listCourse);
            model.addAttribute("user", user);
            return "admin/delete";
        }
        else {
            userService.deleteUser(Integer.parseInt(id));
            return "redirect:/tms/staffs/";
        }
    }

    @RequestMapping(value = "/{id}/deleteanyway")
    public String deleteAnyWhere(@PathVariable String id, Model model) {
        courseService.changeManager(Integer.parseInt(id), 1);
        userService.changeManagerTrainee(Integer.parseInt(id), 1);
        userService.changeManagerTrainer(Integer.parseInt(id), 1);
        userService.deleteUser(Integer.parseInt(id));
        return "redirect:/tms/staffs/";
    }

    @RequestMapping(value = "/{id}/change")
    public String change(@PathVariable String id, Model model) {
        User user = userService.findOneUser(Integer.parseInt(id));
        model.addAttribute("user", user);
        List<User> listStaff = userService.getAllUserByRole(2);
        listStaff.remove(user);
        model.addAttribute("listStaff", listStaff);
        return "admin/changemanager";
    }

    @RequestMapping(value = "/{userIdOld}/managers/{userIdNew}/change")
    public String changeManager(@PathVariable String userIdOld, @PathVariable String userIdNew, Model model) {
        courseService.changeManager(Integer.parseInt(userIdOld), Integer.parseInt(userIdNew));
        userService.changeManagerTrainee(Integer.parseInt(userIdOld), Integer.parseInt(userIdNew));
        userService.changeManagerTrainer(Integer.parseInt(userIdOld), Integer.parseInt(userIdNew));
        userService.deleteUser(Integer.parseInt(userIdOld));
        return "redirect:/tms/staffs/";
    }
}

