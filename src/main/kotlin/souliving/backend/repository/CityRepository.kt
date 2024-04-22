package souliving.backend.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import souliving.backend.model.locations.City

@Repository
interface CityRepository : CoroutineCrudRepository<City, Long>
