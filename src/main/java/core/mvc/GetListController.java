package core.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.QuestionDao;
import next.model.Question;

public class GetListController extends AbstractController {
	QuestionDao questionDao = new QuestionDao();
	List<Question> questions;
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		questions = questionDao.findAll();
		
		return jsonView().addObject("questions", questions);
	}
}
