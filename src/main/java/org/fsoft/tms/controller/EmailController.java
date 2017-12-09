package org.fsoft.tms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by thehaohcm on 5/27/17.
 */
@RestController
public class EmailController {
    //Change variable username, password, host smtp in file application.properties before use it
    @Autowired
    MailSender mailSender;

    @RequestMapping(value="/email/trigger",method = RequestMethod.POST)
    public String triggerEmail(@RequestParam("sendTo") String sendTo, @RequestParam("username") String username,
                               @RequestParam("password") String password){
        SimpleMailMessage message=new SimpleMailMessage();
        Date date=new Date();
        message.setSubject("Tài khoản của bạn tại hệ thống TMS đã được tạo");
        message.setText("Xin chào bạn, đây là mail tự động được gửi vào lúc "+date.toLocaleString()
                +" từ hệ thống TMS nhằm thông báo về việc tài khoản của bạn đã được tạo với thông tin như sau: \n" +
                "Tên đăng nhập: "+username+"\nPassword: "+password
                +"\nTrang đăng nhập: http://localhost:8080"
                +"\nKhi đăng nhập vào lần đầu trên hệ thống, bạn vui lòng thay đổi password.\n"
                +"Xin chân thành cảm ơn\n" +
                "Lưu ý: Đây là hộp thư tự động, bạn không cần trả lời email này");
        message.setTo(sendTo);
        //input into parameter in method setFrom same as variable spring.mail.username in file application.properties
        message.setFrom("email");
        try{
            mailSender.send(message);
            return "Email sent";
        }catch (Exception ex){
            mailSender.send(message);
            ex.printStackTrace();
            return "Have a error";
        }
    }
}
