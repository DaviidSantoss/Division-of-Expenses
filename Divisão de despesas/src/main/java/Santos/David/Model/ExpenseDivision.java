package Santos.David.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "expensedivision")
public class ExpenseDivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long costId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private double valueExpense;

    @Column(nullable = false)
    private double valuePaid;

    public ExpenseDivision(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getValueExpense() {
        return valueExpense;
    }

    public void setValueExpense(double valueExpense) {
        this.valueExpense = valueExpense;
    }

    public double getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(double valuePaid) {
        this.valuePaid = valuePaid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseDivision that = (ExpenseDivision) o;
        return Double.compare(valueExpense, that.valueExpense) == 0 && Double.compare(valuePaid, that.valuePaid) == 0 && Objects.equals(id, that.id) && Objects.equals(costId, that.costId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costId, userId, valueExpense, valuePaid);
    }
}
