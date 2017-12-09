package org.fsoft.tms.controller;

import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.Role;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.service.LoginService;
import org.fsoft.tms.service.RoleService;
import org.fsoft.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by DELL on 5/26/2017.
 */
@Controller
//@RequestMapping(value = "/tms")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name=auth.getName();
            CurrentUser currentUser = CurrentUser.getInstance();
            currentUser.setUser(loginService.findUserByUsername(name));
            if(!currentUser.getUser().isActive())
                return "redirect:/login?error";
            Role role = currentUser.getUser().getRole();
            switch(role.getName()){
                case "ROLE_ADMIN":
                    //return "redirect:/admin";
                    return "admin/index";
                case "ROLE_TS":
                    //return "redirect:/staff";
                    return "staff/index";
                case "ROLE_TER":
                    //return "redirect:/trainer";
                    model.addAttribute("user", loginService.findUserByUsername(name));
                    return "trainer/index";
                case "ROLE_TEE":
                    return "logout";
            }

        }
        return "index1";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @RequestMapping("/staff")
    public String staff() {
        return "staff/index";
    }

    @RequestMapping("/trainer")
    public String trainer(Model model) {
        CurrentUser currentUser = CurrentUser.getInstance();
        User user1 = userService.findOneUser(currentUser.getUser().getId());
        model.addAttribute("user", user1);
        return "trainer/index";
    }


    @RequestMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @RequestMapping("/login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }


}
