package dk.chokobanan.flixbuster.repository;

import dk.chokobanan.flixbuster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
