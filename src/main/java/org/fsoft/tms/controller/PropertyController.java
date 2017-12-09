package org.fsoft.tms.controller;

import org.fsoft.tms.entity.Property;
import org.fsoft.tms.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 5/24/2017.
 */
@Controller
@RequestMapping(value = "/demo/server/property")
public class PropertyController {
    @Autowired
    private PropertyService property;

    @RequestMapping(value = "/getall")
    public String getAllCourse(Model model) {
        model.addAttribute("listProperty", property.getAllProperty());
        return "property";
    }

    @RequestMapping(value = "/add")
    public String getPageAddCourse(Model model) {
        model.addAttribute("property", new Property());
        model.addAttribute("listProperty", property.getAllProperty());
        return "addProperty";
    }

    @RequestMapping(value = "/addProperty")
    public String addCourse(@ModelAttribute Property c) {
        property.addProperty(c);
        return "redirect:/demo/server/property/getall";
    }
//
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
