package dk.chokobanan.flixbuster.repository.postgresql;

import dk.chokobanan.flixbuster.model.postgresql.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findOneByEmail (@Param("email") String email);

    Account findOneByUuid (@Param("uuid") UUID uuid);
}
