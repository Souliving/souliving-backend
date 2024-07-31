package souliving.backend.unit.service

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import souliving.backend.model.locations.Metro
import souliving.backend.repository.MetroRepository
import souliving.backend.service.MetroService

@SpringBootTest(classes = [MetroService::class])
class MetroServiceTest(
    @MockBean
    private var metroRepository: MetroRepository,
    @Autowired
    private var metroService: MetroService
) : ShouldSpec({

    extension(SpringExtension)

    should("Get all metros should return valid metros") {
        val expectedMetros = flowOf(
            Metro(1L, "Тверская", 1L),
            Metro(2L, "Спасская", 2L)
        )

        given(metroRepository.findAll()).willReturn(expectedMetros)

        val result = metroService.getMetros()

        result shouldBe expectedMetros
    }

    should("Get all metros by city id should return valid metros") {
        val cityId = 2L
        val expectedMetros = flowOf(
            Metro(3L, "Василеостровская", 2L)
        )

        given(metroRepository.findAllByCityId(cityId)).willReturn(expectedMetros)

        val result = metroService.getAllMetroByCityId(cityId)

        result shouldBe expectedMetros
    }

    should("Get metros by name should return valid metro") {
        val name = "Чкаловская"
        val expectedMetro = Metro(1L, "Чкаловская", 2L)

        given(metroRepository.findMetroByName(name)).willReturn(expectedMetro)

        val result = metroService.getMetroByName(name)

        result shouldBe expectedMetro
    }

    should("Get metro by id should return valid metro") {
        val id = 2L
        val expectedMetro = Metro(id, "Чкаловская", 2L)

        given(metroRepository.findById(id)).willReturn(expectedMetro)

        val result = metroService.getMetroById(id)

        result shouldBe expectedMetro
    }
})
