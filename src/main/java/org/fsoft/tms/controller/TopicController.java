package org.fsoft.tms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.CurrentUser;
import org.fsoft.tms.entity.Category;
import org.fsoft.tms.entity.Course;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.event.OnAssignTopicCompleteEvent;
import org.fsoft.tms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by DELL on 5/27/2017.
 */
@Controller
@RequestMapping(value = "/tms/topics")
public class TopicController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPropertyService userPropertyService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = "/")
    public String getPageIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
            model.addAttribute("course", new Course());
            if(user.getRole().getId() == 2) {
                model.addAttribute("listTopic", topicService.getAllTopicByStaff());
                model.addAttribute("listCourse", courseService.getAllCourseByStaff(user));
            }
            else {
                model.addAttribute("listTopic", topicService.getAllTopic());
                model.addAttribute("listCourse", courseService.getAllCourse());
            }
        }
        return "topic/index";
    }

    @RequestMapping(value = "/courses")
    public String filter(@ModelAttribute Course c, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            User user =loginService.findUserByUsername(name);
            Course course = courseService.findOneCourse(c.getId());
            model.addAttribute("role", user.getRole());
            model.addAttribute("course", new Course());
            if(user.getRole().getId() == 2) {
//                model.addAttribute("listTopic", topicService.getAllTopicByStaff());
                model.addAttribute("listCourse", courseService.getAllCourseByStaff(user));
            }
            else {
//                model.addAttribute("listTopic", topicService.getAllTopic());
                model.addAttribute("listCourse", courseService.getAllCourse());
            }
            model.addAttribute("listTopic", topicService.findAllTopicByCourse(course));
        }
        return "topic/index";
    }

    @RequestMapping(value = "/add")
    public String getPageAdd(Model model) {
        model.addAttribute("topic", new Topic());
        model.addAttribute("listCourse", courseService.getAllCourseByStaff(CurrentUser.getInstance().getUser()));
        return "topic/add";
    }

    @RequestMapping(value = "/addTopic")
    public String getPageAdd(@ModelAttribute Topic topic) {
        topic.setActive(true);
        topicService.addTopic(topic);
        return "redirect:/tms/topics/";
    }

    @RequestMapping(value = "/{id}/update")
    public String getPageAdd(@PathVariable String id, Model model) {
        Topic topic = topicService.finOneById(Integer.parseInt(id));
        model.addAttribute("topic", topic);
        return "topic/update";
    }

    @RequestMapping(value = "/update")
    public String update(@ModelAttribute Topic topic) {
        topicService.updateTopic(topic);
        return "redirect:/tms/topics/";
    }

    @RequestMapping(value = "/{id}/delete")
    public String getPageDelete(@PathVariable String id) {
        topicService.deleteTopic(Integer.parseInt(id));
        return "redirect:/tms/topics/";
    }

    @RequestMapping(value = "/{id}/trainers")
    public String addTrainer(@PathVariable String id, Model model) {
        model.addAttribute("topic", topicService.finOneById(Integer.parseInt(id)));
        List<User> arr =  userService.getAllUserByRoleAndManager(3, CurrentUser.getInstance().getUser().getId());
        model.addAttribute("listTrainer", arr);
        return "topic/addtrainer";
    }

    @RequestMapping(value = "/{id}/trainers/change")
    public String changeTrainer(@PathVariable String id, Model model) {
        Topic topic = topicService.finOneById(Integer.parseInt(id));
        model.addAttribute("topic", topic);
        List<User> arr =  userService.getAllUserByRoleAndManager(3, CurrentUser.getInstance().getUser().getId());
        arr.remove(topic.getTrainer());
        model.addAttribute("listTrainer", arr);
        return "topic/addtrainer";
    }

    @RequestMapping(value="/{topicID}/trainers/{trainerID}")
    public String adđTrainerToTopic(@PathVariable("trainerID") String trainerID, @PathVariable("topicID") String topicID, Model model) {

        try{
            eventPublisher.publishEvent(new OnAssignTopicCompleteEvent(Integer.parseInt(trainerID), Integer.parseInt(topicID)));
            topicService.addTrainerToTopic(Integer.parseInt(topicID), Integer.parseInt(trainerID));
        }catch (Exception ex)
        {
            logger.debug("problem here!");
        }

//        String email = userPropertyService.getUserProperty(userService.findOneUser(Integer.parseInt(trainerID)), propertyService.findOneProperty(10)).getValue();
//        Topic topic = topicService.finOneById(Integer.parseInt(topicID));
//        User user=userService.findOneUser(Integer.parseInt(trainerID));
//        SimpleMailMessage message=new SimpleMailMessage();
//        Date date=new Date();
//        message.setSubject("Tài khoản của bạn tại hệ thống TMS đã được thêm vào topic "+topic.getTitle());
//        message.setText("Xin chào bạn, đây là thư tự động được gửi vào lúc "+date.toLocaleString()
//                +" từ hệ thống TMS nhằm thông báo về việc tài khoản '"+user.getUsername()
//                +"' của bạn đã được thêm vào topic tạo với thông tin như sau: \n"
//                +"Tên topic: "+ topic.getTitle()+" thuộc category "+topic.getCourse()+"\n"
//                +"Nội dung: \n"
//                +topic.getContent()+"\n"
//                +"Bạn vui lòng đăng nhập vào hệ thống TMS ở địa chỉ: http://localhost:8080 với tài khoản "+user.getUsername()
//                +" để sử dụng\n"
//                +"Lưu ý: Đây là hộp thư tự động, bạn không cần trả lời email này");
//        message.setTo(email);
//        //input into parameter in method setFrom same as variable spring.mail.username in file application.properties
//        message.setFrom("email");
//        try{
//            mailSender.send(message);
//        }catch (Exception ex){
//            mailSender.send(message);
//            ex.printStackTrace();
//        }
        return "redirect:/tms/topics/";
    }

    @RequestMapping(value="/search")
    public String searchTopicStaff(@RequestParam String q, Model model){
        if(q.equals(""))
            return "redirect:/tms/topics/";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null) {
            String name = auth.getName();
            user =loginService.findUserByUsername(name);
            model.addAttribute("role", user.getRole());
        }
        model.addAttribute("listTopic",topicService.searchTopic(q, user));
        return "topic/index";
    }
}