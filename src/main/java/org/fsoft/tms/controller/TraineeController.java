package org.fsoft.tms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.Property;
import org.fsoft.tms.entity.TraineeInfo;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by thehaohcm on 5/30/17.
 */
@Controller
@RequestMapping(value="/tms/trainees")
public class TraineeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;


    @Autowired
    private UserPropertyService userPropertyService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private CourseService courseService;

    private final Logger logger = LogManager.getLogger();

    @RequestMapping(value="/")
    public String getPageIndex(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = "";
        if (auth != null) {
            name = auth.getName();
        }
        User user = loginService.findUserByUsername(name);
        List<User> arr;
        if(user.getRole().getName().equals("ROLE_ADMIN"))
            arr = userService.getAllUserByRole(4);
        else
            arr = userService.getAllUserByRoleAndManager(4,user.getId());
        List<TraineeInfo> listTrainee = new ArrayList<>();
        for(int i = 0; i < arr.size(); i++) {
            TraineeInfo traineeInfo = new TraineeInfo();
            User u = arr.get(i);
            traineeInfo.setUser(u);
            traineeInfo.setName(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(1)).getValue());
            traineeInfo.setBirthDate(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(2)).getValue());
            traineeInfo.setEducation(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(3)).getValue());
            traineeInfo.setProgrammingLanguage(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(4)).getValue());
            traineeInfo.setToeicScore(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(5)).getValue());
            traineeInfo.setExperienceDetail(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(6)).getValue());
            traineeInfo.setDepartment(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(7)).getValue());
            traineeInfo.setLocation(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(12)).getValue());
            listTrainee.add(traineeInfo);
        }
        model.addAttribute("role",user.getRole());
        model.addAttribute("listTrainee",listTrainee);
        return "trainee/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model) {
        if (q.equals("")) {
            return "redirect:/tms/trainees/";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null) {
            String name = auth.getName();
            user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
        }
        List<User> arr = userService.search(q, 4, user);
        List<TraineeInfo> listTrainee = new ArrayList<>();
        for(int i = 0; i < arr.size(); i++) {
            TraineeInfo traineeInfo = new TraineeInfo();
            User u = arr.get(i);
            traineeInfo.setUser(u);
            traineeInfo.setName(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(1)).getValue());
            traineeInfo.setBirthDate(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(2)).getValue());
            traineeInfo.setEducation(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(3)).getValue());
            traineeInfo.setProgrammingLanguage(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(4)).getValue());
            traineeInfo.setToeicScore(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(5)).getValue());
            traineeInfo.setExperienceDetail(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(6)).getValue());
            traineeInfo.setDepartment(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(7)).getValue());
            traineeInfo.setLocation(userPropertyService.getUserProperty(u,
                    propertyService.findOneProperty(12)).getValue());
            listTrainee.add(traineeInfo);
        }
        model.addAttribute("role",user.getRole());
        model.addAttribute("listTrainee",listTrainee);
        return "trainee/index";
    }

    @RequestMapping(value="/{id}/profile")
    public String getPageProfile(@PathVariable String id, Model model){
        User user=userService.findOneUser(Integer.parseInt(id));
        model.addAttribute("trainee",user);
        return "trainee/profile";
    }

    @RequestMapping(value = "/{id}/update")
    public String getPageUpdate(@PathVariable String id, Model model) {
        User user = userService.findOneUser(Integer.parseInt(id));
        User userTemp=new User();
        userTemp.setId(user.getId());
        userTemp.setUsername(user.getUsername());
        userTemp.setPassword(user.getPassword());
        TraineeInfo traineeInfo = new TraineeInfo();
        traineeInfo.setUser(userTemp);
        traineeInfo.setName(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(1)).getValue());
        traineeInfo.setBirthDate(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(2)).getValue());
        traineeInfo.setEducation(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(3)).getValue());
        traineeInfo.setProgrammingLanguage(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(4)).getValue());
        traineeInfo.setToeicScore(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(5)).getValue());
        traineeInfo.setExperienceDetail(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(6)).getValue());
        traineeInfo.setDepartment(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(7)).getValue());
        traineeInfo.setLocation(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(12)).getValue());
        model.addAttribute("trainee", traineeInfo);
        return "trainee/update";
    }

    @RequestMapping(value="/update")
    public String updateProfile(@ModelAttribute TraineeInfo traineeInfo){
        User user=userService.findOneUser(traineeInfo.getUser().getId());
        String encryptedPass= userService.encode(traineeInfo.getUser().getPassword());
        if(!traineeInfo.getUser().getPassword().trim().equals("")&&!user.getPassword().equals(encryptedPass))
            userService.updateUser(traineeInfo.getUser(),true);
        else
            userService.updateUser(traineeInfo.getUser(),false);
        userService.saveTrainee(traineeInfo);
        return "redirect:/tms/trainees/";
    }

    @RequestMapping(value="/add")
    public String getPageAdd(Model model){
        User user =new User();
        TraineeInfo traineeInfo=new TraineeInfo();
        traineeInfo.setUser(user);
        model.addAttribute("trainee",traineeInfo);
        return "trainee/add";
    }

    @RequestMapping(value="/addTrainee")
    public String addTrainee(Model model, @ModelAttribute TraineeInfo traineeInfo){
        if(!userService.checkUsername(traineeInfo.getUser().getUsername())){
            User user =new User();
            traineeInfo.setUser(user);
            model.addAttribute("trainee",traineeInfo);
            model.addAttribute("error","Username is exited!");
            return "trainee/add";
        }
        userService.addTrainee(traineeInfo.getUser(), CurrentUser.getInstance().getUser().getId());
        userService.saveTrainee(traineeInfo);
        return "redirect:/tms/trainees/";
    }

    @RequestMapping(value="/{id}/delete")
    public String deleteTrainee(@PathVariable String id){
        User user=userService.findOneUser(Integer.parseInt(id));
        user.setActive(false);
        userService.unAssignTraineeToCourse(Integer.parseInt(id));
        userService.saveUser(user);
        return "redirect:/tms/trainees/";
    }

    @RequestMapping(value="/{id}/recover")
    public String recoverTrainee(@PathVariable String id){
        User user=userService.findOneUser(Integer.parseInt(id));
        user.setActive(true);
        userService.saveUser(user);
        return "redirect:/tms/trainees/";
    }
}
