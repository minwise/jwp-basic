package core.mvc;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView implements View {
	private static final Logger log = LoggerFactory.getLogger(JsonView.class);

	@Override
	public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(model));
	}
	
	private Map<String, Object> createModel(HttpServletRequest req) {
		Enumeration<String> names = req.getAttributeNames();
		Map<String, Object> model = new HashMap<>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			model.put(name, req.getAttribute(name));
		}
		return model;
	}

}
