package souliving.backend.service

import souliving.backend.model.Properties

interface PropertiesService {

    suspend fun getPropertiesById(id: Long): Properties?

    suspend fun createProperties(prop: Properties): Properties?

}