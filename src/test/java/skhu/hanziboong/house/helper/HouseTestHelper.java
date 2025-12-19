package skhu.hanziboong.house.helper;

import static skhu.hanziboong.house.fixture.HouseFixture.DORMITORY;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;

@Component
public class HouseTestHelper {

    private final HouseRepository houseRepository;

    public HouseTestHelper(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Transactional
    public House initHouseData() {
        House house = DORMITORY.create();
        
        return houseRepository.save(house);
    }
}
