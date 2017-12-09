package org.fsoft.tms.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.event.OnAssignTopicCompleteEvent;
import org.fsoft.tms.service.PropertyService;
import org.fsoft.tms.service.TopicService;
import org.fsoft.tms.service.UserPropertyService;
import org.fsoft.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@Component
public class AssignTopicListener implements ApplicationListener<OnAssignTopicCompleteEvent>{

    @Autowired
    private UserService userService;

    @Autowired
    private UserPropertyService userPropertyService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private MailSender mailSender;

    private final Logger logger = LogManager.getLogger();

    @Override
    @Async
    public void onApplicationEvent(OnAssignTopicCompleteEvent onAssignTopicCompleteEvent) {
        sendMail(onAssignTopicCompleteEvent);
    }


    public void sendMail(OnAssignTopicCompleteEvent event){
        String email = userPropertyService.getUserProperty(userService.findOneUser(event.getTrainerId()), propertyService.findOneProperty(10)).getValue();
//        logger.debug("email:"+email +";userid:"+event.getTrainerId().toString()+";topicid:"+event.getTopicId().toString());
        Topic topic = topicService.finOneById(event.getTopicId());
        User user=userService.findOneUser(event.getTrainerId());
        SimpleMailMessage message=new SimpleMailMessage();
        Date date=new Date();
        message.setSubject("Tài khoản của bạn tại hệ thống TMS đã được thêm vào topic "+topic.getTitle());
        message.setText("Xin chào bạn, đây là thư tự động được gửi vào lúc "+date.toLocaleString()
                +" từ hệ thống TMS nhằm thông báo về việc tài khoản "+user.getUsername()
                +" của bạn đã được thêm vào topic tạo với thông tin như sau: \n"
                +"Tên topic: "+ topic.getTitle()+" thuộc khóa học "+topic.getCourse().getName()+"\n"
                +"Nội dung: \n"
                +topic.getContent()+"\n"
                +"Bạn vui lòng đăng nhập vào hệ thống TMS ở địa chỉ: http://localhost:8080 với tài khoản "+user.getUsername()
                +" để sử dụng\n"
                +"Lưu ý: Đây là hộp thư tự động, bạn không cần trả lời email này");
        message.setTo(email);
        //input into parameter in method setFrom same as variable spring.mail.username in file application.properties
        message.setFrom("email");
        try{
            mailSender.send(message);
        }catch (Exception ex){
            mailSender.send(message);
            ex.printStackTrace();
        }
    }
}
