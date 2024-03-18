package souliving.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import souliving.backend.logger.logResponse
import souliving.backend.mail.ReceiverRequest
import souliving.backend.service.EmailService

@RestController
@RequestMapping("api/v1/email")
class EmailController(private val emailService: EmailService) {

    @GetMapping("/text")
    fun sendHtmlEmail() {
        val email = listOf("dorohovgeorge@gmail.com", "blohina02@icloud.com")
        val request = ReceiverRequest(email)
        logResponse("send email to $email")
        emailService.sendHtmlEmail(request)
    }
}
