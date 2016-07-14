package core.mvc;

import java.util.HashMap;
import java.util.Map;

import minho.controller.CreateController;
import minho.controller.ForwardController;
import minho.controller.HomeController;
import minho.controller.ListController;
import minho.controller.LoginController;
import minho.controller.LogoutController;
import minho.controller.UpdateController;

public class RequestMapping {
	Map<String, Controller> controllers = new HashMap<>();
	
	public RequestMapping() {
		controllers.put("", new HomeController());
		controllers.put("/users/login", new LoginController());
		controllers.put("/users/loginForm", new ForwardController("/user/login.jsp"));
		controllers.put("/users/update", new UpdateController());
		controllers.put("/users/updateForm", new UpdateController());
		controllers.put("/users", new ListController());
		controllers.put("/users/create", new CreateController());
		controllers.put("/users/form", new ForwardController("/user/form.jsp"));
		controllers.put("/users/logout", new LogoutController());
	}
	
	public Controller getController(String requestUrl) {
		return controllers.get(requestUrl);
	}
}
