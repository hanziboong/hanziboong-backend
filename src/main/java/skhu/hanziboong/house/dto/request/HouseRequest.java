package skhu.hanziboong.house.dto.request;

import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.domain.HouseType;

public record HouseRequest(
        String type
) {
    public House toHouse() {
        HouseType houseType = HouseType.getType(type);
        return new House(houseType);
    }
}
