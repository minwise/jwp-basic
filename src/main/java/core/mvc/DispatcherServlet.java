package core.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import minho.controller.HomeController;

@WebServlet(name = "dispatcher", urlPatterns = {"", "/"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			RequestMapping mapper = new RequestMapping();
			String url;

			Controller controller = mapper.getController(req.getRequestURI());
			if (controller == null) {
				controller = new HomeController();
			}

			url = controller.execute(req, resp);
			
			if (url.startsWith("redirect:")) {
				String[] tokens = url.split(":");
				log.debug("url : {}", tokens[1]);
				resp.sendRedirect(tokens[1]);
				return;
			}
			log.debug("url : {}", url);
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
