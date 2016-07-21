package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

public class AddAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	private AnswerDao answerDao = AnswerDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), 
				req.getParameter("contents"), 
				Long.parseLong(req.getParameter("questionId")));
		
		Answer savedAnswer = answerDao.insert(answer);
		
		ModelAndView mav = jsonView();
		mav.addObject("answer", savedAnswer);
		
		// 이곳에 8번 선택 사항 넣어야됨
		return mav;
	}
}
