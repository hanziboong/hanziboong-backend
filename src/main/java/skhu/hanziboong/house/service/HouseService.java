package skhu.hanziboong.house.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.dto.request.HouseRequest;
import skhu.hanziboong.house.dto.response.HouseCreateResponse;
import skhu.hanziboong.house.repository.HouseRepository;

@RequiredArgsConstructor
@Service
public class HouseService {

    private final HouseRepository houseRepository;

    @Transactional
    public HouseCreateResponse createHouse(HouseRequest request) {
        House house = houseRepository.save(request.toHouse());

        return HouseCreateResponse.from(house);
    }
}
