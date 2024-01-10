package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.logger.logResponse
import souliving.backend.model.form.ShortForm
import souliving.backend.service.fake.FakeShortFormService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/shortForm")
class ShortFormController(private var shortFormService: FakeShortFormService) {

    @GetMapping("/")
    fun getAllShortForms(): Flow<ShortForm> =
        shortFormService.getAllShortForm()?.let {
            logResponse("Get all Short Forms")
            return@let it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Didn't find short forms"
        )

    @GetMapping("/getShortFormByFormId/{id}")
    suspend fun getShortFormByFormId(@PathVariable id: Long): ShortForm? =
        shortFormService.getShortFormByFormId(id)?.let {
            logResponse("Get Short Form by Form id: $id")
            return@let it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Didn't find short form by form id: $id"
        )

    @GetMapping("/getShortFormByUserId/{id}")
    suspend fun getShortFormByUserId(@PathVariable id: Long): ShortForm? =
        shortFormService.getShortFormByUserId(id)?.let {
            logResponse("Get Short Form by User id: $id")
            return@let it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Didn't find short form by User id: $id"
        )

}
