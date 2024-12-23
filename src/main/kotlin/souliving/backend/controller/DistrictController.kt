package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.logger.logResponse
import souliving.backend.model.locations.District
import souliving.backend.service.DistrictService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/district")
class DistrictController(private var districtService: DistrictService) {
    @GetMapping("/")
    fun getAllDistricts(): Flow<District> =
        districtService.getDistricts().also {
            logResponse("Find All districts")
        }

    @GetMapping("/{id}")
    suspend fun getAllDistrictByCityId(@PathVariable id: Long): Flow<District> =
        districtService.getAllDistrictByCityId(id)?.let {
            logResponse("Found district with id: $id")
            it
        } ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "City with id $id")
}
