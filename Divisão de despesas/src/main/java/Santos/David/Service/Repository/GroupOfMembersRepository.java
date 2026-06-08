package Santos.David.Service.Repository;

import Santos.David.Model.GroupOfMembers;
import Santos.David.Model.Groups;
import Santos.David.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupOfMembersRepository extends JpaRepository<GroupOfMembers,Long> {

    boolean existsByGroupAndUser(Groups group, User user);
}
