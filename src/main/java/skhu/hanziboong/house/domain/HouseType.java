package skhu.hanziboong.house.domain;

public enum HouseType {
    MONTHLY_RENT,
    DORMITORY,
    SHARE_HOUSE;

    public static HouseType getType(String type) {
        return HouseType.valueOf(type);
    }
}
