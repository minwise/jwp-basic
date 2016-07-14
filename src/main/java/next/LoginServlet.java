package next;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;

//@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = DataBase.findUserById(req.getParameter("userId"));
		if (user == null) {
			log.debug("User is not found");
			resp.sendRedirect("/user/login_failed.html");
		} else {
			if (user.login(req.getParameter("password"))) {
				HttpSession session = req.getSession();
				session.setAttribute("logined",	"true");
				log.debug("Success login!!!");
				resp.sendRedirect("/index.html");
			} else {
				log.debug("Password is not correct!!!");
				resp.sendRedirect("/user/login_failed.html");
			}
		}
	}
	
}
