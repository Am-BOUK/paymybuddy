package pay.my.buddy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pay.my.buddy.service.IOperationMetier;

@Controller
public class PayMyBuddyController {
	@Autowired
	private IOperationMetier payMyBuddyMetier;
	
	@RequestMapping("/home/operations")
	public String index() {
		return "comptes";
	}

}
