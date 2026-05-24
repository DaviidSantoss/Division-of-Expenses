package Santos.David.Model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "group")
public class Group {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 80)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdby;

    @Column(nullable = false)
    private Date createdin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group(){

    }

    public User getCreatedby() {
        return createdby;
    }

    public void setCreatedby(User createdby) {
        this.createdby = createdby;
    }

    public Date getCreatedin() {
        return createdin;
    }

    public void setCreatedin(Date createdin) {
        this.createdin = createdin;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && createdby == group.createdby && Objects.equals(name, group.name) && Objects.equals(createdin, group.createdin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdby, createdin);
    }
}
