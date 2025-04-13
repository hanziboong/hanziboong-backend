package skhu.hanziboong.rule.dto.response;

import lombok.Builder;
import skhu.hanziboong.rule.domain.Rule;

@Builder
public record RuleResponse(
        Long id,
        String title,
        String description,
        Long authorId,
        String authorNickname,
        Long houseId
) {
    public static RuleResponse from(Rule rule) {
        return RuleResponse.builder()
                .id(rule.getId())
                .title(rule.getTitle())
                .description(rule.getDescription())
                .authorId(rule.getAuthorId())
                .authorNickname(rule.getAuthorNickname())
                .houseId(rule.getHouseId())
                .build();
    }
}
