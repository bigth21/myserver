package bigth.myserver.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "select ur from UserRole ur " +
            "inner join fetch ur.role " +
            "where ur.user = :user")
    List<UserRole> findAuthorities(@Param("user") User user);
}
