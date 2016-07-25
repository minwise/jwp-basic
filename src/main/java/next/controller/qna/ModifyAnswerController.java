package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

public class ModifyAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(ModifyAnswerController.class);
	
	AnswerDao answerDao = new AnswerDao();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Answer answer = answerDao.findById(Long.parseLong(req.getParameter("answerId")));
		answerDao.update(answer, req.getParameter("contents"));
		return jspView("redirect:/qna/show?questionId=" + req.getParameter("questionId"));
	}

}
