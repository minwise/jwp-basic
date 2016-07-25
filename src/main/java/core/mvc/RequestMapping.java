package core.mvc;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.HomeController;
import next.controller.qna.AddAnswerController;
import next.controller.qna.AddQuestionController;
import next.controller.qna.ApiDeleteQuestionController;
import next.controller.qna.DeleteAnswerController;
import next.controller.qna.DeleteQuestionController;
import next.controller.qna.ListQuestionController;
import next.controller.qna.ModifyAnswerController;
import next.controller.qna.ShowController;
import next.controller.qna.UpdateAnswerController;
import next.controller.user.CreateUserController;
import next.controller.user.ListUserController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateFormUserController;
import next.controller.user.UpdateUserController;

public class RequestMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private Map<String, Controller> mappings = new HashMap<>();
	
	void initMapping() {
		mappings.put("/", new HomeController());
	    mappings.put("/users/form", new ForwardController("/user/form.jsp"));
	    mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
	    mappings.put("/users", new ListUserController());
		mappings.put("/users/login", new LoginController());
		mappings.put("/users/profile", new ProfileController());
	    mappings.put("/users/logout", new LogoutController());
	    mappings.put("/users/create", new CreateUserController());
	    mappings.put("/users/updateForm", new UpdateFormUserController());
	    mappings.put("/users/update", new UpdateUserController());
	    mappings.put("/qna/form", new ListQuestionController());
		mappings.put("/qna/show", new ShowController());
		mappings.put("/qna/create", new AddQuestionController());
		mappings.put("/qna/deleteQuestion", new DeleteQuestionController());
		mappings.put("/api/qna/addAnswer", new AddAnswerController());
		mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());
		mappings.put("/api/qna/list", new GetListController());
		mappings.put("/api/qna/updateAnswer", new UpdateAnswerController());
		mappings.put("/api/qna/modifyAnswer", new ModifyAnswerController());
		mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController());

		logger.info("Initialized Request Mapping!");
	}
	
	public Controller findController(String url) {
		return mappings.get(url);
	}
	
	void put(String url, Controller controller) {
		mappings.put(url, controller);
	}

}
