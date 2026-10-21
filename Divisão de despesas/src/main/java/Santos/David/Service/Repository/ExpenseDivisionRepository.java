package Santos.David.Service.Repository;

import Santos.David.Model.Cost;
import Santos.David.Model.ExpenseDivision;
import Santos.David.Model.Groups;
import Santos.David.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.Optional;

public interface ExpenseDivisionRepository extends JpaRepository<ExpenseDivision,Long> {


    Optional<ExpenseDivision> findByCostAndUser(Cost cost, User user);

    @Query("""
    SELECT COALESCE(SUM(e.valueExpense), 0)
    FROM ExpenseDivision e
    WHERE e.user = :user
    AND e.cost.groups = :group
""")
    BigDecimal sumValueExpenseByUserAndGroup(
            @Param("user") User user,
            @Param("group") Groups group
    );

}
