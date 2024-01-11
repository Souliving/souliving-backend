package souliving.backend.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.logger.logResponse
import souliving.backend.model.home.HomeOwner
import souliving.backend.service.fake.FakeHomeOwnerService

@RestController
@CrossOrigin
@RequestMapping("/api/v1/homeOwner")
class HomeOwnerController(private var homeOwnerService: FakeHomeOwnerService) {


    @GetMapping("/")
    fun getAllHomeOwners(): Flow<HomeOwner> =
        homeOwnerService.getAllHomeOwners()?.let {
            logResponse("Find All home owners")
            return@let it
        } ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find home owners")


    @GetMapping("/getHomeOwnerById/{id}")
    suspend fun getHomeOwnerById(@PathVariable id: Long): HomeOwner =
        homeOwnerService.getHomeOwnerById(id)?.let {
            logResponse("Find home owner by id: $id")
            return@let it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find home owner by id: $id"
        )


    @GetMapping("/getHomeOwnersByHomeTypeId/{id}")
    suspend fun getHomeOwnersByHomeTypeId(@PathVariable id: Long): Flow<HomeOwner> =
        homeOwnerService.getHomeOwnersByHomeId(id)?.let {
            logResponse("Find home owners by home type id: $id")
            return@let it
        } ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Didn't find home owner by home type  id: $id"
        )
}
