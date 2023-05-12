package QLKS.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import QLKS.Entity.*;

public interface UserRepository extends CrudRepository<User, Long>{

}