package skhu.hanziboong.expense.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.hanziboong.global.BaseEntity;
import skhu.hanziboong.house.domain.House;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToBuyItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    public ToBuyItem(String name) {
        validateNotNull(name);
        this.name = name;
        this.checked = false;
    }

    public void check() {
        this.checked = !this.checked;
    }

    public void linkTo(House house) {
        this.house = house;
    }

    private void validateNotNull(String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("사야 할 물건의 제목은 비어있을 수 없습니다.");
        }
    }
}
