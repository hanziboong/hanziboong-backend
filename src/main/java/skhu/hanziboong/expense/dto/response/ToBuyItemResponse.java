package skhu.hanziboong.expense.dto.response;

import java.util.List;
import lombok.Builder;
import skhu.hanziboong.expense.domain.ToBuyItem;
import skhu.hanziboong.house.domain.House;

@Builder
public record ToBuyItemResponse(
        Long id,
        String name,
        Boolean checked
) {

    public static List<ToBuyItemResponse> from(House house) {
        return house.getToBuyItems().stream()
                .map(ToBuyItemResponse::from)
                .toList();
    }

    public static ToBuyItemResponse from(ToBuyItem toBuyItem) {
        return ToBuyItemResponse.builder()
                .id(toBuyItem.getId())
                .name(toBuyItem.getName())
                .checked(toBuyItem.getChecked())
                .build();
    }
}
