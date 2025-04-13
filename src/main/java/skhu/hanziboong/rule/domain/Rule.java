package skhu.hanziboong.rule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.hanziboong.global.BaseEntity;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Rule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    public Rule(String title, String description, Member author) {
        this(title, description, author, author.getHouse());
    }

    private Rule(String title, String description, Member author, House house) {
        validateNotBlank(title);
        this.title = title;
        this.description = description;
        this.author = author;
        this.house = house;
    }

    private void validateNotBlank(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("규칙 제목, 규칙 설명은 비어있을 수 없습니다.");
        }
    }

    public void update(String title, String description) {
        validateNotBlank(title);
        this.title = title;
        this.description = description;
    }

    public Long getAuthorId() {
        return author.getId();
    }

    public String getAuthorNickname() {
        return author.getNickname();
    }

    public Long getHouseId() {
        return house.getId();
    }
}
