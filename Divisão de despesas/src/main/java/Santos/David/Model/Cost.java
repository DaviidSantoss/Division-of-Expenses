package Santos.David.Model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "cost")
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groups", nullable = false)
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_for", nullable = false)
    private User paidFor;

    @Column(nullable = false)
    private  String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    private TypeOfDivision type;


    @Column(nullable = false)
    private LocalDateTime createdIn;


    public enum TypeOfDivision{
        EQUALITY,
        BY_VALUE

        }


}
