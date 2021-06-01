package dk.chokobanan.flixbuster.repository.postgresql;

import dk.chokobanan.flixbuster.model.postgresql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByUuid (@Param("uuid") UUID uuid);


}
