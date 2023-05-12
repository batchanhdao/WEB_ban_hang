package QLKS.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import QLKS.Entity.Room;
import QLKS.Repository.RoomRepository;

@Controller
@RequestMapping("/room") 
public class RoomController {
	@Autowired
	private RoomRepository roomRepo;

	// xem danh sách các phòng
	@GetMapping // xử lý yêu cầu HTTP trên đường dẫn giống mức lớp
	public String viewList(Model model, HttpSession session) {
		List<Room> rooms = (List<Room>) roomRepo.findAll();
		model.addAttribute("rooms", rooms);
		return "roomList"; 
	}

	// xem chi tiết 1 phòng sử dụng id 
	@GetMapping("/details/{id}") // 
	public String viewDetails(Model model, @PathVariable("id") Long id, HttpSession session) {
		Room room = roomRepo.findById(id).orElse(null); 
		model.addAttribute("room", room);
		return "roomDetails";
	}
}
