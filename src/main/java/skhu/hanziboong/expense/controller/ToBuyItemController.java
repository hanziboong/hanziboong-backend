package skhu.hanziboong.expense.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.expense.controller.docs.ToBuyItemApiDocs;
import skhu.hanziboong.expense.dto.request.ToBuyItemsRequest;
import skhu.hanziboong.expense.dto.response.ToBuyItemResponse;
import skhu.hanziboong.expense.service.ToBuyItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/toBuy")
public class ToBuyItemController implements ToBuyItemApiDocs {

    private final ToBuyItemService toBuyItemService;

    @Override
    @PostMapping("/house/{id}")
    public ResponseEntity<Void> createToBuyItems(@PathVariable Long id,
                                                 @RequestBody ToBuyItemsRequest request) {
        toBuyItemService.createToBuyItemByHouseId(id, request);
        return ResponseEntity.created(URI.create("/api/to-buy-items")).build();
    }

    @Override
    @GetMapping("/house/{id}")
    public ResponseEntity<List<ToBuyItemResponse>> getToBuyItems(@PathVariable Long id) {
        List<ToBuyItemResponse> responses = toBuyItemService.getToBuyItemsByHouseId(id);
        return ResponseEntity.ok(responses);
    }

    @Override
    @PatchMapping("/{id}/check")
    public ResponseEntity<ToBuyItemResponse> checkItem(@PathVariable Long id) {
        ToBuyItemResponse response = toBuyItemService.checkById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateItemName(@PathVariable Long id,
                                               @RequestParam String name) {
        toBuyItemService.updateToBuyItemNameById(id, name);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        toBuyItemService.deleteToBuyItemById(id);
        return ResponseEntity.noContent().build();
    }
}
