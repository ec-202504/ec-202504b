package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *  参考 http://qiita.com/ry-s/items/4ce9a5856461119a4718
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTestMail() {
        System.out.println("---メールの送信処理---");
        var mailInfo = new SimpleMailMessage();
        mailInfo.setSubject("javaの実装練習です");
        mailInfo.setText("お元気ですかテストです");
        mailInfo.setTo("");
        mailInfo.setFrom("xxxx@gmail.com");
        mailSender.send(mailInfo);

    }

    public void orderdMail(String toMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "注文を受けつけました";
        message.setTo(toMail); // 宛先
        message.setSubject(subject); // 件名
        message.setText("注文を受けつけました  ---"); // 本文

        mailSender.send(message);
    }
}
