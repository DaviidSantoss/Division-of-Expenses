package Santos.David.Service.Repository;

import Santos.David.Model.GroupOfMembers;
import Santos.David.Model.Groups;
import Santos.David.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupOfMembersRepository extends JpaRepository<GroupOfMembers,Long> {

    boolean existsByGroupsAndUser(Groups groups, User user);

    List<GroupOfMembers> findByGroups(Groups groups);
}
