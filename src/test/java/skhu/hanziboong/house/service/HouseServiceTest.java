package skhu.hanziboong.house.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import skhu.hanziboong.global.ServiceTest;
import skhu.hanziboong.house.dto.request.HouseRequest;
import skhu.hanziboong.house.dto.response.HouseCreateResponse;

@DisplayName("집 서비스 계층 테스트")
@ServiceTest
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

        assertThat(response.id()).isNotNull();
    }
}
