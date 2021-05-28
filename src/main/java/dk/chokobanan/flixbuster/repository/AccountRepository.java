package dk.chokobanan.flixbuster.repository;

import dk.chokobanan.flixbuster.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
