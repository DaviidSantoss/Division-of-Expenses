package Santos.David.Service.Repository;

import Santos.David.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.enabled = false WHERE u.id = :id")
    @Transactional
    void disableUser(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.enabled = true")
    Page<User> findUserEnabled(Pageable pageable);

}
