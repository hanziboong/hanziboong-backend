package skhu.hanziboong.rule.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.rule.dto.request.RuleRequest;
import skhu.hanziboong.rule.dto.response.RuleCreateResponse;
import skhu.hanziboong.rule.dto.response.RuleResponse;
import skhu.hanziboong.rule.service.RuleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService ruleService;

    @PostMapping
    public ResponseEntity<Void> createRule(
            @RequestBody RuleRequest request
    ) {
        RuleCreateResponse response = ruleService.createRule(request);

        return ResponseEntity.created(URI.create("/api/rules/" + response.id())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleResponse> findRule(@PathVariable Long id) {
        return ResponseEntity.ok(ruleService.findRuleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRule(
            @PathVariable Long id,
            @RequestBody RuleRequest request
    ) {
        ruleService.updateRuleById(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        ruleService.deleteRuleById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
