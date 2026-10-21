package Santos.David.Service.Repository;

import Santos.David.Model.Cost;
import Santos.David.Model.Groups;
import Santos.David.Model.Payment;
import Santos.David.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

public interface PaymentRepository  extends JpaRepository<Payment,Long> {

    @Query("""
    SELECT COALESCE(SUM(p.value), 0)
    FROM Payment p
    WHERE p.payer = :user
    AND p.cost.groups = :group
""")
    BigDecimal sumValueByUserAndGroup(
            @Param("user") User user,
            @Param("group") Groups group
    );

    @Query("""
    SELECT COALESCE(SUM(p.value), 0)
    FROM Payment p
    WHERE p.payer = :payer
    AND p.cost = :cost
""")
    BigDecimal sumValueByPayerAndCost(
            @Param("payer") User payer,
            @Param("cost") Cost cost
    );
}
