package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.logger.logResponse
import souliving.backend.model.locations.Metro
import souliving.backend.service.fake.FakeMetroService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/metro")
class MetroController(private val metroService: FakeMetroService) {
    @GetMapping("/")
    fun getAllMetro(): Flow<Metro> =
        metroService.getMetros().also {
            logResponse("Get all Metro's")
        }

    @GetMapping("/getAllMetroByCityId/{cityId}")
    suspend fun getAllMetroByCityId(@PathVariable cityId: Long): Flow<Metro>? =
        metroService.getAllMetroByCityId(cityId)?.let {
            if (it.count() == 0) {
                throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Didn't metro by id: $cityId")
            }
            logResponse("Get metro by cityId: $cityId")
            it
        }

    @GetMapping("/getMetroByName/{name}")
    suspend fun getMetroByName(@PathVariable name: String): Metro =
        metroService.getMetroByName(name)?.let {
            logResponse("Get metro by name: $name")
            it
        } ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Didn't metro by name: $name")
}
