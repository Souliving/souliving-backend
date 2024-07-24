package souliving.backend.unit.service

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import souliving.backend.model.locations.City
import souliving.backend.repository.CityRepository
import souliving.backend.service.CityService

@SpringBootTest(classes = [CityService::class])
class CityServiceTest(
    @MockBean
    private var cityRepository: CityRepository,
    @Autowired
    private var cityService: CityService
) : ShouldSpec({

    extension(SpringExtension)

    should("Get city by id should return a valid city") {
        val cityId = 1L
        val expectedCity = City(1L, "Ufa")

        given(cityRepository.findById(cityId)).willReturn(expectedCity)

        val result = cityService.getCityById(1L)

        result shouldBe expectedCity
    }

    should("Get all cities should return all valid cities") {
        val expectedCities = flowOf(City(1L, "Ufa"), City(2L, "SPB"))

        given(cityRepository.findAll()).willReturn(expectedCities)

        val result = cityService.getCities()

        result shouldBe expectedCities
    }

})
