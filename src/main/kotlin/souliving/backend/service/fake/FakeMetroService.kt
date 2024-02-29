package souliving.backend.service.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import org.springframework.stereotype.Service
import souliving.backend.model.locations.Metro
import souliving.backend.service.MetroService

@Service
class FakeMetroService : MetroService {
    private val metro: Flow<Metro> =
        flowOf(
            Metro(0, "Горьковская", 1),
            Metro(1, "Арбат", 0),
            Metro(2, "Лесная", 1),
        )

    override fun getMetros(): Flow<Metro> {
        return metro
    }

    override suspend fun getAllMetroByCityId(cityId: Long): Flow<Metro>? =
        metro.filter { it.cityId == cityId }

    override suspend fun getMetroByName(name: String): Metro? =
        metro.filter { it.name == name }.firstOrNull()

    override suspend fun getMetroById(id: Long?): Metro? =
        metro.filter { it.id == id }.firstOrNull()
}
