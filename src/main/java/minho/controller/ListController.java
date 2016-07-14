package minho.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import core.mvc.Controller;
import next.controller.UserSessionUtils;

public class ListController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(ListController.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			log.debug("is not login");
			return "redirect:/users/loginForm";
		}
		
		log.debug("login!");
		
        req.setAttribute("users", DataBase.findAll());

        return "/user/list.jsp";
	}

}
