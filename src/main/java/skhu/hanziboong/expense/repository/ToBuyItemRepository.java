package skhu.hanziboong.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import skhu.hanziboong.expense.domain.ToBuyItem;

public interface ToBuyItemRepository extends JpaRepository<ToBuyItem, Long> {
}
