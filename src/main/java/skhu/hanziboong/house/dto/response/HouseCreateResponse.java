package skhu.hanziboong.house.dto.response;

import skhu.hanziboong.house.domain.House;

public record HouseCreateResponse(
        Long id
) {
    public static HouseCreateResponse from(House house) {
        return new HouseCreateResponse(house.getId());
    }
}
