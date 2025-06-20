package skhu.hanziboong.expense.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import skhu.hanziboong.expense.domain.ToBuyItem;

public interface ToBuyItemRepository extends JpaRepository<ToBuyItem, Long> {

    @Query("select tbi from ToBuyItem tbi where tbi.house.id = :houseId")
    List<ToBuyItem> findByHouse_id(@Param("houseId") Long houseId);
}
