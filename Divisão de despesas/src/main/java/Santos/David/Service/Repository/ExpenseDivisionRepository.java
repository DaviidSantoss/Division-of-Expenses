package Santos.David.Service.Repository;

import Santos.David.Model.Cost;
import Santos.David.Model.ExpenseDivision;
import Santos.David.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseDivisionRepository extends JpaRepository<ExpenseDivision,Long> {

    Optional<ExpenseDivision> findByCostAndUser(Cost cost, User user);
}
