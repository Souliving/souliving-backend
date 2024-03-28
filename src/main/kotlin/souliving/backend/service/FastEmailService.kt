package souliving.backend.service

import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.api.mailer.config.TransportStrategy
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import org.simplejavamail.springsupport.SimpleJavaMailSpringSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Service
import souliving.backend.mail.EmailBody
import souliving.backend.mail.ReceiverRequest


@Service
@Import(
    SimpleJavaMailSpringSupport::class
)
class FastEmailService(
    @Autowired
    private var mailer: Mailer
) {
    init {
        mailer = MailerBuilder
            .withSMTPServer("smtp.gmail.com", 587, "soulflatmate", "ieaa nwew ytth apej")
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .withSessionTimeout(10 * 1000)
            .clearEmailValidator()
            .async()
            .buildMailer()
    }


    fun sendEmail(request: ReceiverRequest, emailBody: EmailBody) {
        val email = EmailBuilder.startingBlank()
            .to("", request.receivers)
            .from("soulflatmate@gmail.com")
            .withSubject(emailBody.subject)
            .withPlainText(emailBody.text)
            .buildEmail()

        mailer.validate(email)
        mailer.sendMail(email)
    }
}
