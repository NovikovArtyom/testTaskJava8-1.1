import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
    public static void sendEmailWithAttachment(String attachmentPath, String emailTo) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.senderEmail, Constants.senderPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Constants.senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
        message.setSubject("Тестовое задание 1.1");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Архив директории тестового проекта.");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(attachmentPath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(attachmentPath);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);

        System.out.println("Письмо успешно отправлено!");
    }
}
