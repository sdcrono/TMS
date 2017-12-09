package org.fsoft.tms.controller;

import org.fsoft.tms.entity.User;
import org.fsoft.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 5/24/2017.
 */
@Controller
@RequestMapping(value = "/demo/server/user")
public class UserController {
    @Autowired
    private UserService user;

    @RequestMapping(value = "/getall")
    public String getAllCourse(Model model) {
        model.addAttribute("listUser", user.getAllUser());
        return "user";
    }

    @RequestMapping(value = "/add")
    public String getPageAddCourse(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUser", user.getAllUser());
        return "addUser";
    }

    @RequestMapping(value = "/addUser")
    public String addCourse(@ModelAttribute User c) {
        c.setActive(true);
//        user.addUser(c);
        return "redirect:/demo/server/user/getall";
    }

    @RequestMapping(value = "/addProperty")
    public String addRolePermission() {
        user.addPropertyForUser();
        return "redirect:/demo/server/user/getall";
    }

    @RequestMapping(value = "/addRole")
    public String addRole() {
        user.addTopic();
        return "redirect:/demo/server/user/getall";
    }
//    @RequestMapping(value = "/update/{id}")
//    public String updateCourse(@PathVariable String id, Model model) {
//        Role c = role.findOneCourse(Integer.parseInt(id));
//        model.addAttribute("role", c);
//        model.addAttribute("listRole", role.getAllRole());
//        return "updateRole";
//    }
//
//    @RequestMapping(value = "/updateRole")
//    public String updateCourse(@ModelAttribute Course c) {
//        role.(c);
//        return "redirect:/demo/server/role/getall";
//    }
}
