package QLKS.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import QLKS.Entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	Optional<Account> findByUsername(String username);

}
