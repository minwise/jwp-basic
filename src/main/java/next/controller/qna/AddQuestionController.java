package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

public class AddQuestionController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(AddQuestionController.class);
	
	private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		
		Question question = new Question(req.getParameter("writer"), 
				req.getParameter("title"), req.getParameter("contents"));
		
		Question savedQuestion = questionDao.insert(question);
		return jspView("redirect:/");
	}

}
