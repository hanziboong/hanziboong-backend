package skhu.hanziboong.expense.dto.request;

import java.util.List;

public record ToBuyItemsRequest(
        Long houseId,
        List<String> itemNames
) {
}
