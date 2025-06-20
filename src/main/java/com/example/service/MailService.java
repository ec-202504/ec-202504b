package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void orderdMail(String toMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "注文を受けつけました";
        message.setTo(toMail); // 宛先
        message.setSubject(subject); // 件名
        message.setText("注文を受けつけました  ---"); // 本文
        message.setFrom("shunsuke.kobayashi@rakus.co.jp"); // Gmailのアドレス

        mailSender.send(message);
    }
}
