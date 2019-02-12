package com.jia.storage.service.impl;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Users;
import com.jia.storage.mapper.UsersMapper;
import com.jia.storage.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    UsersMapper usersMapper;

    private static final String FROM_ADDRESS= "2294161634@qq.com";

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @RabbitListener(queues = "regist")
    public void sendRegistMail(Message message) {
        try {
            String str = new String(message.getBody());
            Users user = JSON.parseObject(str, Users.class);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("注册成功！");
            helper.setText("恭喜您, 注册成功! \n您的用户名为: "+ user.getUsername());
            helper.setTo(user.getEmail());
            helper.setFrom(FROM_ADDRESS);

            javaMailSender.send(mimeMessage);

            // helper.addAttachment("", new File("")); 在邮件中添加附件
        } catch (MessagingException e) {
            logger.error("注册邮件发送失败！"+ e.toString());
        }
    }

    @RabbitListener(queues = "remove")
    public void sendDeleteMail(Message message) {
        try {
            String str = new String(message.getBody());
            Users user = JSON.parseObject(str, Users.class);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("账户删除提醒");
            helper.setText("您的账户"+ user.getUsername()+"被删除, 请检查是否为本人操作!");
            helper.setTo(user.getEmail());
            helper.setFrom(FROM_ADDRESS);

            javaMailSender.send(mimeMessage);
            // helper.addAttachment("", new File("")); 在邮件中添加附件
        } catch (MessagingException e) {
            logger.error("注册邮件发送失败！"+ e.toString());
        }
    }
}
