package skhu.hanziboong.house.fixture;

import java.util.function.Supplier;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.domain.HouseType;

public enum HouseFixture {
    DORMITORY(() -> new House(HouseType.DORMITORY));

    private final Supplier<House> supplier;

    HouseFixture(Supplier<House> supplier) {
        this.supplier = supplier;
    }

    public House create() {
        return supplier.get();
    }
}
