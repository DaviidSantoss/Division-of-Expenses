package Santos.David.Service.Repository;

import Santos.David.Model.ExpenseDivision;
import Santos.David.Model.Payment;
import Santos.David.Service.PaymentService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment,Long> {
}
