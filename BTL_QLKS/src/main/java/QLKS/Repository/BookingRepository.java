package QLKS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import QLKS.Entity.Booking;
import QLKS.Entity.Client;
import QLKS.Entity.Room;
import QLKS.Entity.User;


public interface BookingRepository extends CrudRepository<Booking, Long>{

	List<Booking> findAllByClient(Client client);
	List<Booking> findAllByRoom(Room room);
	Optional<Booking> findByClient(Client client);
	
}
