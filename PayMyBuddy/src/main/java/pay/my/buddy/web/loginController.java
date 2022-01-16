package pay.my.buddy.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
	Logger Logger = LoggerFactory.getLogger(loginController.class);

	/**
	 * 
	 * @return login web page
	 */
	@GetMapping("/login")
	public String login() {
		Logger.info("get loging");
		return "login.html";
	}

	/**
	 * 
	 * @return home web page
	 */
	@GetMapping("/home")
	public String home() {
		Logger.info("get home");
		return "home.html";
	}

}
