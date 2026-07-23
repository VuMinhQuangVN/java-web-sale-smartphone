/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 *
 * @author Lenovo
 */
public class EmailService {

    public void sendOtp(String toEmail, String otp) {
        String from = "ngocquyenle12102004@gmail.com";
        String password = "dxab uhqc tzrn uowb";

        Properties props = new Properties();
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Thiếu dòng này code không biết gửi đi đâu
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Mã OTP khôi phục mật khẩu");
            msg.setText("Mã OTP của bạn là: " + otp + "\nCó hiệu lực trong 5 phút.");
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
