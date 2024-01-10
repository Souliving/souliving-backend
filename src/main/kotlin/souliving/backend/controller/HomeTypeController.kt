package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.logger.logResponse
import souliving.backend.model.home.HomeType
import souliving.backend.service.fake.FakeHomeTypeService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/homeType")
class HomeTypeController(private val homeTypeService: FakeHomeTypeService) {

    @GetMapping("/")
    private fun getAllHomeTypes(): Flow<HomeType> =
        homeTypeService.getAllHomeTypes()?.let {
            logResponse("Find All home types")
            return@let it
        } ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find home types")

    @GetMapping("/getHomeTypeById/{id}")
    private suspend fun getHomeTypeById(@PathVariable id: Long): HomeType =
        homeTypeService.getHomeTypeById(id)?.let {
            logResponse("Find home type by id: $id")
            return@let it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find home type by id: $id"
        )
}
