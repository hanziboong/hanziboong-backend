package skhu.hanziboong.expense.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.expense.domain.ToBuyItem;
import skhu.hanziboong.expense.dto.request.ToBuyItemsRequest;
import skhu.hanziboong.expense.dto.response.ToBuyItemResponse;
import skhu.hanziboong.expense.repository.ToBuyItemRepository;
import skhu.hanziboong.global.exception.CustomException;
import skhu.hanziboong.global.exception.ErrorCode;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ToBuyItemService {

    private final HouseRepository houseRepository;
    private final ToBuyItemRepository toBuyItemRepository;

    @Transactional
    public void createToBuyItemByHouseId(Long houseId, String itemName) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        ToBuyItem toBuyItem = ToBuyItem.create(itemName, house);

        toBuyItemRepository.save(toBuyItem);
    }

    @Transactional
    public ToBuyItemResponse checkById(Long id) {
        ToBuyItem toBuyItem = toBuyItemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TO_BUY_ITEM_EXCEPTION,
                        ErrorCode.NOT_FOUND_TO_BUY_ITEM_EXCEPTION.getMessage()));

        toBuyItem.check();

        return ToBuyItemResponse.from(toBuyItem);
    }

    @Transactional
    public List<ToBuyItemResponse> getToBuyItemsByHouseId(Long id) {
        List<ToBuyItem> toBuyItems = toBuyItemRepository.findByHouse_id(id);

        return ToBuyItemResponse.from(toBuyItems);
    }

    @Transactional
    public void updateToBuyItemNameById(Long id, String name) {
        ToBuyItem toBuyItem = toBuyItemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TO_BUY_ITEM_EXCEPTION,
                        ErrorCode.NOT_FOUND_TO_BUY_ITEM_EXCEPTION.getMessage()));

        toBuyItem.updateName(name);
    }

    @Transactional
    public void deleteToBuyItemById(Long id) {
        toBuyItemRepository.deleteById(id);
    }
}
