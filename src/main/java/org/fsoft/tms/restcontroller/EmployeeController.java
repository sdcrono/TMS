package org.fsoft.tms.restcontroller;

import org.fsoft.tms.service.UserService;
import org.fsoft.tms.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by DELL on 6/2/2017.
 */
@RestController
@RequestMapping(value = "/user")
public class EmployeeController {
    @Autowired
    UserService userService;

    @RequestMapping("/getall")
    public List<User> getAll() {
        return userService.getAllUser();
    }
}
