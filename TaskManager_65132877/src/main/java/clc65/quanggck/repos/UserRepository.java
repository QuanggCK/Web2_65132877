package clc65.quanggck.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import clc65.quanggck.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}