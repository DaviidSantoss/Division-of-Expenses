package Santos.David.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cost")
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private  Long groupId;

    private Long paidFor;

    private  String description;

    private double  totalValue;

    @Enumerated(EnumType.STRING)
    private TypeOfDivision type;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    private LocalDateTime createdin;

    public Cost() {
    }


    public enum TypeOfDivision{
        EQUALITY,
        BY_VALUE

        }
    public enum PaymentStatus {
        PENDING,
        PARTIALLY_PAID,
        SETTLED
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupid) {
        this.groupId = groupid;
    }

    public long getPaidfor() {
        return paidFor;
    }

    public void setPaidfor(long paidfor) {
        this.paidFor = paidfor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalvalue() {
        return totalValue;
    }

    public void setTotalvalue(double totalvalue) {
        this.totalValue = totalvalue;
    }

    public TypeOfDivision getType() {
        return type;
    }

    public void setType(TypeOfDivision type) {
        this.type = type;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedin() {
        return createdin;
    }

    public void setCreatedin(LocalDateTime createdin) {
        this.createdin = createdin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return id == cost.id && groupId == cost.groupId && paidFor == cost.paidFor && Double.compare(totalValue, cost.totalValue) == 0 && Objects.equals(description, cost.description) && type == cost.type && status == cost.status && Objects.equals(createdin, cost.createdin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, paidFor, description, totalValue, type, status, createdin);
    }
}
