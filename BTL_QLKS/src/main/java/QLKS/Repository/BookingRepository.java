package QLKS.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import QLKS.Entity.Booking;
import QLKS.Entity.Client;
import QLKS.Entity.Room;


public interface BookingRepository extends CrudRepository<Booking, Long>{

	List<Booking> findAllByClient(Client client);

	List<Booking> findAllByRoom(Room room);

}
