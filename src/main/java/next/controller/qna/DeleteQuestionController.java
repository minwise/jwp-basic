package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class DeleteQuestionController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);
	
	QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession httpSession = req.getSession();
		if (!UserSessionUtils.isLogined(httpSession)) {
			return jspView("redirect:/users/loginForm");
		}
		
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findById(questionId);
		User user = UserSessionUtils.getUserFromSession(httpSession);
		
		if (question.getCountOfComment() == 0 && user.getUserId() == question.getWriter()) {
			questionDao.delete(questionId);
		}
		return jspView("redirect:/");
	}

}
