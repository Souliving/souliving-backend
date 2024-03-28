package souliving.backend.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import souliving.backend.logger.logResponse
import souliving.backend.mail.EmailBody
import souliving.backend.mail.ReceiverRequest
import souliving.backend.service.FastEmailService

@RestController
@RequestMapping("api/v1/email")
class EmailController(private val emailService: FastEmailService) {

    @PostMapping("/text")
    suspend fun sendHtmlEmail(
        @RequestBody emailBody: EmailBody
    ) {
        val email = listOf("dorohovgeorge@gmail.com", "blohina02@icloud.com")
        val request = ReceiverRequest(email)
        logResponse("send email to $email")
        emailService.sendEmail(request, emailBody)
    }
}
