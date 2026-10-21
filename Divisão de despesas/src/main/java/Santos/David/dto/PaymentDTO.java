package Santos.David.dto;

import Santos.David.Model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PaymentDTO {


    private Long id;
    private Long costId;
    private Long payerId;
    private  User receiver;
    private Long receiverId;
    private BigDecimal value;
    private LocalDateTime paidIn;
}
