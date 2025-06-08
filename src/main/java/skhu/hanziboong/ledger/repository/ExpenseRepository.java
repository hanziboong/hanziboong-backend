package skhu.hanziboong.ledger.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.ledger.domain.Expense;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
    SELECT DISTINCT e FROM Expense e
    LEFT JOIN FETCH e.participants p
    LEFT JOIN FETCH p.participantMember
    WHERE e.house = :house
    """)
    List<Expense> findAllByHouseWithParticipants(@Param("house") House house);
}
