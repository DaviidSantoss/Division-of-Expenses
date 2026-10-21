package Santos.David.Service.Repository;

import Santos.David.Model.Cost;
import Santos.David.Model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CostRepository extends JpaRepository<Cost,Long> {

    List<Cost> findByGroups(Groups groups);
}
