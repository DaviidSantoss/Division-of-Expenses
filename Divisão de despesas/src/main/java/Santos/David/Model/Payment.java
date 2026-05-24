package Santos.David.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long costId;

    @Column(nullable = false)
    private Long payerId;

    @Column(nullable = false)
    private  Long receiverId;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private LocalDateTime paidIn;

    public Payment(){

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

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getPaidIn() {
        return paidIn;
    }

    public void setPaidIn(LocalDateTime paidIn) {
        this.paidIn = paidIn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Double.compare(value, payment.value) == 0 && Objects.equals(id, payment.id) && Objects.equals(costId, payment.costId) && Objects.equals(payerId, payment.payerId) && Objects.equals(receiverId, payment.receiverId) && Objects.equals(paidIn, payment.paidIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costId, payerId, receiverId, value, paidIn);
    }
}
