package QLKS.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import QLKS.Entity.Booking;
import QLKS.Entity.Room;
import QLKS.Repository.BookingRepository;
import QLKS.Repository.RoomRepository;

@Controller
@RequestMapping("/manage/room") 
@SessionAttributes("alteredRoom") 
public class ManageRoomController {

	@Autowired 
	private RoomRepository roomRepo;
	
	@Autowired 
	private BookingRepository bookingRepo;

	@ModelAttribute("alteredRoom")
	public Room room() {
		return new Room();
	}

	@GetMapping // xử lý yêu cầu HTTP trên đường dẫn "/manage/room"
	public String manageRoomFrm(Model model) {
		List<Room> rooms = (List<Room>) roomRepo.findAll();
		model.addAttribute("rooms", rooms);
		return "manageRoomList"; 
	}

//	xem chi tiết phòng 
	@GetMapping("/details/{id}") // xử lý yêu cầu HTTP trên đường dẫn "/manage/room/details/{id}"
	public String manageRoomDetails(Model model, @PathVariable("id") Long id) {
		Room room = roomRepo.findById(id).orElse(null);
		model.addAttribute("room", room);
		return "manageRoomDetails"; // chuyển đến trang xem cụ thể 1 phòng
	}

//	thay đổi thông tin phòng theo id
	@GetMapping("/change/{id}") // xử lý yêu cầu HTTP trên đường dẫn "/manage/room/change/{id}"
	public String changeRoomInfo(Model model, @PathVariable("id") Long id,
			@SessionAttribute("alteredRoom") Room alteredroom) {
		Room room = roomRepo.findById(id).orElse(null);
		if (room != null) { // nếu có phòng thì cập nhập id và tên phòng vào alteredroom trong session
			alteredroom.setId(room.getId());
			alteredroom.setName(room.getName());
		}
		model.addAttribute("room", room);
		return "changeRoomDetails";
	}

	@PostMapping("/change") // tiếp nhận data thay đổi phòng trong SessionAttribute("alteredRoom")
	public String confirmChange(Room room, @SessionAttribute("alteredRoom") Room alteredroom) {
		alteredroom.setPrice(room.getPrice());
		alteredroom.setType(room.getType());
		alteredroom.setDescription(room.getDescription());
		roomRepo.save(alteredroom);
		return "redirect:/manage/room"; 
	}

//	tiếp nhận yêu cầu thêm phòng
	@GetMapping("/add") // xử lý yêu cầu HTTP trên đường dẫn "/manage/room/add"
	public String addRoom(Model model) {
		model.addAttribute("room", new Room());
		return "manageAddRoom";
	}

//	tiếp nhận data từ đường dẫn "/manage/room/add"
	@PostMapping("/add")
	public String saveRoom(Room room) { // lưu vào csdl rồi chuyển đến phương thức manageRoomFrm()
		roomRepo.save(room);
		return "redirect:/manage/room";
	}

	// tiếp nhận yêu cầu xóa phòng
	@GetMapping("/delete/{id}")
	public String deleteRoom(@PathVariable("id") Long id) {
		Room room = roomRepo.findById(id).orElse(null);
		List<Booking> bookings = bookingRepo.findAllByRoom(room);
		for(Booking booking : bookings) {
			bookingRepo.deleteById(booking.getId());
		}
		roomRepo.deleteById(id);
		return "redirect:/manage/room";

	}

}
