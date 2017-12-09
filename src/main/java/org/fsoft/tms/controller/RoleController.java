package org.fsoft.tms.controller;

import org.fsoft.tms.entity.Role;
import org.fsoft.tms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 5/24/2017.
 */
@Controller
@RequestMapping(value = "/demo/server/role")
public class RoleController {
    @Autowired
    private RoleService role;

    @RequestMapping(value = "/getall")
    public String getAllCourse(Model model) {
        model.addAttribute("listRole", role.getAllRole());
        return "role";
    }

    @RequestMapping(value = "/add")
    public String getPageAddCourse(Model model) {
        model.addAttribute("role", new Role());
        model.addAttribute("listRole", role.getAllRole());
        return "addRole";
    }

    @RequestMapping(value = "/addRole")
    public String addCourse(@ModelAttribute Role c) {
        role.addRole(c);
        return "redirect:/demo/server/role/getall";
    }

    @RequestMapping(value = "/addPermission")
    public String addRolePermission() {
        role.addPermissionForRole();
        return "redirect:/demo/server/role/getall";
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
