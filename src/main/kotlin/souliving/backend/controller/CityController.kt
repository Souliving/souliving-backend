package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.logger.logResponse
import souliving.backend.model.locations.City
import souliving.backend.service.CityService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/cities")
class CityController(private var cityService: CityService) {

    @GetMapping("/")
    fun getAllCities(): Flow<City> =
        cityService.getCities().also {
            logResponse("Find All cities")
        }

    @GetMapping("/{id}")
    suspend fun getCityById(@PathVariable id: Long): City =
        cityService.getCityById(id)?.let {
            logResponse("Find city with id: $id")
            it
        } ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "City with id $id not found")

}
