package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;

public class ListQuestionController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(ListQuestionController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		
		ModelAndView mav = jspView("/qna/form.jsp");
		mav.addObject("user", UserSessionUtils.getUserFromSession(req.getSession()));
		
		return mav;
	}

}
