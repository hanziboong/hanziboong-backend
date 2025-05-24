package skhu.hanziboong.house.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class HouseTest {

    @DisplayName("적절한 타입이 주어지면 집을 생성할 수 있다.")
    @ParameterizedTest
    @EnumSource(HouseType.class)
    void createHouse(HouseType type) {
        assertThatCode(() -> new House(type)).doesNotThrowAnyException();
    }
}