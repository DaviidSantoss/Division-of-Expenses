package Santos.David.dto;

import Santos.David.Model.Cost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CostDTO {


    private Long id;
    private Long groupId;
    private Long paidFor;
    private String description;
    private BigDecimal totalValue;
    private Cost.TypeOfDivision type;
    private LocalDateTime createdIn;
    private List<DivisionItemDTO> divisions;


    public enum TypeOfDivision{
        EQUALITY,
        BY_VALUE
    }
}
