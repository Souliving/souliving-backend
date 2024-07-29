package souliving.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.CreatePropertiesDto
import souliving.backend.logger.logResponse
import souliving.backend.model.Properties
import souliving.backend.service.PropertiesService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/properties")
class PropertiesController(private var propertiesService: PropertiesService) {

    @GetMapping("/getPropertiesById/{id}")
    suspend fun getPropertiesById(@PathVariable id: Long): Properties =
        propertiesService.getPropertiesById(id)?.let {
            logResponse("Find properties by id: $id")
            it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find properties by id: $id"
        )
}
