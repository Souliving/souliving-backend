package souliving.backend.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import souliving.backend.model.home.HomeType

@Repository
interface HomeTypeRepository : CoroutineCrudRepository<HomeType, Long>
