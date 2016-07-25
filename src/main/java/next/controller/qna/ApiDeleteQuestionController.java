package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;
import next.model.User;

public class ApiDeleteQuestionController extends AbstractController {
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
		
		ModelAndView mav = jsonView();
		if (question.getCountOfComment() == 0 && user.getUserId() == question.getWriter()) {
			questionDao.delete(questionId);
			mav.addObject("result", Result.ok());
		} else {
			mav.addObject("result", Result.fail("유저가 다르거나, 댓글이 달려있어서 삭제가 불가능합니다."));
		}
		return mav;
	}

}
