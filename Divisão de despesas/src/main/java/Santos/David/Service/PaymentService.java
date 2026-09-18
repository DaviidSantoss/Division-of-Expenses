package Santos.David.Service;

import Santos.David.Mapper.ObjectMapper;
import Santos.David.Model.Payment;
import Santos.David.Service.Repository.CostRepository;
import Santos.David.Service.Repository.ExpenseDivisionRepository;
import Santos.David.Service.Repository.PaymentRepository;
import Santos.David.Service.Repository.UserRepository;
import Santos.David.dto.PaymentDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final ExpenseDivisionRepository expenseDivisionRepository;
    private final UserRepository userRepository;
    private final CostRepository costRepository;

    public PaymentService(PaymentRepository repository, ExpenseDivisionRepository expenseDivisionRepository, UserRepository userRepository, CostRepository costRepository) {
        this.repository = repository;
        this.expenseDivisionRepository = expenseDivisionRepository;
        this.userRepository = userRepository;
        this.costRepository = costRepository;
    }

    @Transactional
    public PaymentDTO registerPayment(PaymentDTO dto) {

        var findExpense = costRepository.findById(dto.getCostId());
        if (findExpense.isEmpty()) throw new RuntimeException("Expense not found");

        var findUserPayer = userRepository.findById(dto.getPayerId());
        if (findUserPayer.isEmpty()) throw new RuntimeException("User not found");

        var findUserReciver = userRepository.findById(dto.getReceiverId());
        if (findUserReciver.isEmpty()) throw new RuntimeException("User not found");

        var findExpenseDivisionByCostAndUser = expenseDivisionRepository.findByCostAndUser(findExpense.get(), findUserPayer.get());
        if (findExpenseDivisionByCostAndUser.isEmpty()) throw new RuntimeException("Expense not found");


        if (dto.getValue().compareTo(findExpenseDivisionByCostAndUser.get().getValueExpense()) > 0 ) throw new RuntimeException("Value expense not found");

       Payment payment = new Payment();
       payment.setCost(findExpense.get());
       payment.setPayer(findUserPayer.get());
       payment.setReceiver(findUserReciver.get());
       payment.setValue(dto.getValue());
       payment.setPaidIn(LocalDateTime.now());
       repository.save(payment);

        return ObjectMapper.parseObject(payment, PaymentDTO.class);
    }


}
