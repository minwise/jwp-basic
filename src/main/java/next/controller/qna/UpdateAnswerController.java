package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.User;

public class UpdateAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(UpdateAnswerController.class);
	
	AnswerDao answerDao = new AnswerDao();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			log.debug("You need to log in");
			return jspView("redirect:/users/loginForm");
		}
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		Answer answer = answerDao.findById(Long.parseLong(req.getParameter("answerId")));
		if (user.getName().equals(answer.getWriter())) {
			return jspView("/qna/update.jsp").addObject("answer", answer);
		}
		
		return jspView("redirect:/");
	}

}
