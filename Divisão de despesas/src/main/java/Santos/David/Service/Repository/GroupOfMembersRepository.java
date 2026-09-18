package Santos.David.Service.Repository;

import Santos.David.Model.GroupOfMembers;
import Santos.David.Model.Groups;
import Santos.David.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupOfMembersRepository extends JpaRepository<GroupOfMembers,Long> {

    boolean existsByGroupAndUser(Groups group, User user);


    List<GroupOfMembers> findByGroup(Groups group);
}
