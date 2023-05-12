package QLKS.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import QLKS.Entity.Client;
import QLKS.Entity.User;

public interface ClientRepository extends CrudRepository<Client, Long>{
	Optional<Client> findByUser(User user);

}
