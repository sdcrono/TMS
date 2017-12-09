package org.fsoft.tms.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fsoft.tms.entity.Topic;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.event.OnAssignTopicCompleteEvent;
import org.fsoft.tms.event.OnRegistrationCompleteEvent;
import org.fsoft.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private MailSender mailSender;

    @Override
    @Async
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        sendMail(onRegistrationCompleteEvent);
    }

    public void sendMail(OnRegistrationCompleteEvent event){
        Date date=new Date();
        SimpleMailMessage message=new SimpleMailMessage();
//        userService.addUser(trainerInfo.getUser(), 3, trainerInfo.getUser().getManager().getId());
//        userService.saveTrainer(trainerInfo);

        String token= UUID.randomUUID().toString();

//        logger.debug("user:"+event.getUser().getId()+","+event.getUser().getUsername()+","+event.getEmail()+","+token);

        userService.createVerificationToken(event.getUser(),token);

        User user = event.getUser();

        String sendToEmail=event.getEmail();
        String subject="Xác thực tài khoản TMS";
        String body="Đây là email được gửi từ hệ thống TMS vào lúc "+date.toLocaleString()+" thông báo về việc xác thực tài khoản.\n"+
                "Tên tài khoản:"+user.getUsername()+"\n"+
                "Mật khẩu:"+user.getPassword()+"\n"+
                "Bạn vui lòng truy cập vào đường dẫn sau để xác thực tài khoản và thay đổi mật khẩu ngay lập tức:\n"+
                "http://localhost:8080/register/confirm/"+token+"\n"+
                "Thời gian xác thực có hiệu lực 24h tính từ lúc email được gửi - "+date.toLocaleString()+"\n"+
                "Xin cảm ơn\n"+
                "Lưu ý: Đây là hộp thư tự động, bạn không cần trả lời email này.";
        message.setTo(sendToEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("email");
        try{
            mailSender.send(message);
        }catch (Exception ex){
            mailSender.send(message);
            ex.printStackTrace();
        }
    }
}
