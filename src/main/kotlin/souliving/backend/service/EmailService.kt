package souliving.backend.service

import jakarta.mail.internet.InternetAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import souliving.backend.mail.ReceiverRequest

@Service
class EmailService(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.sender.email}") private val senderEmail: String,
    @Value("\${spring.mail.sender.text}") private val senderText: String
) {
    fun sendHtmlEmail(request: ReceiverRequest) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)
        helper.setFrom(InternetAddress(senderEmail, senderText))
        helper.setTo(request.receivers.toTypedArray())
        helper.setSubject("Тест")
        helper.setText("<p><b>Привет</b> Это тест</p>", true)
        javaMailSender.send(message)
    }
}
