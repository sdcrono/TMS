package org.fsoft.tms.controller;

import org.fsoft.tms.entity.Permission;
import org.fsoft.tms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 5/24/2017.
 */
@Controller
@RequestMapping(value = "/demo/server/permission")
public class PermissionController {
    @Autowired
    private PermissionService permission;

    @RequestMapping(value = "/getall")
    public String getAllCourse(Model model) {
        model.addAttribute("listPermission", permission.getAllPermission());
        return "permission";
    }

    @RequestMapping(value = "/add")
    public String getPageAddCourse(Model model) {
        model.addAttribute("permission", new Permission());
        model.addAttribute("listPermission", permission.getAllPermission());
        return "addPermission";
    }

    @RequestMapping(value = "/addPermission")
    public String addCourse(@ModelAttribute Permission c) {
        permission.addPermission(c);
        return "redirect:/demo/server/permission/getall";
    }

    @RequestMapping(value = "/addRole")
    public String addRole() {
        permission.addRole();
        return "redirect:/demo/server/permission/getall";
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
