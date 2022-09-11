package org.drinkless.tdlib.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/manage/home")
	public String ManageHome(Model model) {
		
		return "ManageHome";
	}
	
	@GetMapping("/client/home")
	public String ClientHome(Model model) {
		
		return "ClientHome";
	}
	
	@GetMapping("/client/register")
	public String ClientRegister(Model model) {
		
		return "ClientRegister";
	}
}
