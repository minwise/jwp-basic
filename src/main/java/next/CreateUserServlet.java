package next;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;

//@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(CreateUserServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User(req.getParameter("userId"), req.getParameter("password"),
				req.getParameter("name"), req.getParameter("email")); 
		DataBase.addUser(user);
		log.debug("user created");
		resp.sendRedirect("/index.html");
	}

}
