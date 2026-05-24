package Santos.David.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "groupofmembers")
public class GroupOfMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long groupid;

    @Column(nullable = false)
    private  long userid;

    @Column(nullable = false)
    private Date entered;

    public GroupOfMembers(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public Date getEntered() {
        return entered;
    }

    public void setEntered(Date entered) {
        this.entered = entered;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GroupOfMembers that = (GroupOfMembers) o;
        return id == that.id && groupid == that.groupid && userid == that.userid && Objects.equals(entered, that.entered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupid, userid, entered);
    }
}
