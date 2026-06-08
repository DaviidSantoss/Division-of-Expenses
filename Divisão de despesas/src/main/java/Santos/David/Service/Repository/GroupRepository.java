package Santos.David.Service.Repository;

import Santos.David.Model.Groups;
import Santos.David.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Groups,Long> {

    boolean existsByName(String name);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Groups u SET u.enabled = false WHERE u.id =:id")
    @Transactional
    void disableGroup(@Param("id") Long id);

    @Query("SELECT u FROM Groups u WHERE u.enabled = true")
    Page<Groups> findGroupEnabled(Pageable pageable);
}
