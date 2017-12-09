package org.fsoft.tms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.entity.TrainerInfo;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.entity.VerificationToken;
import org.fsoft.tms.event.OnRegistrationCompleteEvent;
import org.fsoft.tms.service.PropertyService;
import org.fsoft.tms.service.UserPropertyService;
import org.fsoft.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@Controller
@RequestMapping(value = "/register")
public class RegistrationController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private UserPropertyService userPropertyService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/confirm/{token}", method = RequestMethod.GET)
    public String confirmAccount(@PathVariable String token, Model model){
//        User user = userService.getVerificationToken(token).getUser();
        VerificationToken verificationToken = userService.getVerificationToken(token);
        User user;
        if(!verificationToken.isExpired())
            user = verificationToken.getUser();
        else
        {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(verificationToken.getUser(),
                    userPropertyService.getUserProperty(verificationToken.getUser(),
                            propertyService.findOneProperty(10)).getValue()));
            return "resend";
        }

        user.setActive(true);
//        logger.debug("user1:"+user.getId()+","+user.getRole().getName()+","+user.getManager().getUsername()+","+token);
//        User userTemp=new User();
//        userTemp.setId(user.getId());
//        userTemp.setUsername(user.getUsername());
//        userTemp.setPassword(user.getPassword());
//        userTemp.setUserProperties(user.getUserProperties());
//        userTemp.setManager(user.getManager());
//        userTemp.setRole(user.getRole());
        TrainerInfo trainerInfo = new TrainerInfo();
        trainerInfo.setUser(user);
        trainerInfo.setName(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(1)).getValue());
        trainerInfo.setEmail(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(10)).getValue());
        trainerInfo.setPhone(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(9)).getValue());
        trainerInfo.setAddress(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(8)).getValue());
        trainerInfo.setType(userPropertyService.getUserProperty(user,
                propertyService.findOneProperty(11)).getValue());
        model.addAttribute("trainer", trainerInfo);
        return "register";
    }

    @RequestMapping(value = "/change")
    public String changePassword(@ModelAttribute TrainerInfo trainerInfo){
        userService.addUser(trainerInfo.getUser(), 3, trainerInfo.getUser().getManager().getId());
        return "redirect:/login";
    }

    @RequestMapping(value = "/resend")
    public String send(@ModelAttribute String email){
        List<User> users = userService.search(email,3);

        return "redirect:/login";
    }
}
