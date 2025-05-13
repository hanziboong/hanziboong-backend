package skhu.hanziboong.house.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import skhu.hanziboong.house.dto.request.HouseRequest;
import skhu.hanziboong.house.dto.response.HouseCreateResponse;

@Import(value = HouseService.class)
@DataJpaTest
class HouseServiceTest {

    private final HouseService houseService;

    @Autowired
    public HouseServiceTest(HouseService houseService) {
        this.houseService = houseService;
    }

    @DisplayName("집을 생성할 수 있다.")
    @Test
    void createHouse() {
        HouseRequest request = new HouseRequest("DORMITORY");
        HouseCreateResponse response = houseService.createHouse(request);

        assertThat(response.id()).isEqualTo(1L);
    }
}