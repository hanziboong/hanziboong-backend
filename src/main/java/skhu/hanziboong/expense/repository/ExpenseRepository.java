package skhu.hanziboong.expense.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.expense.domain.Expense;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByHouse(House house);
}
