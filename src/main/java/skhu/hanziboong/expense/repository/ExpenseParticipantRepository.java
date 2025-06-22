package skhu.hanziboong.expense.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skhu.hanziboong.expense.domain.ExpenseParticipant;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, Long> {

    @Query("""
    select ep from ExpenseParticipant ep
    JOIN fetch ep.participantMember
    WHERE ep.expense.id = :expenseId
    """)
    List<ExpenseParticipant> findByExpenseId(@Param("expenseId") Long expenseId);

    @Query("""
    select ep from ExpenseParticipant ep
    join fetch ep.participantMember
    where ep.expense.id in :expenseIds
    """)
    List<ExpenseParticipant> findByExpenseIds(@Param("expenseIds") List<Long> expenseIds);

    void deleteByExpense_Id(Long id);
}
