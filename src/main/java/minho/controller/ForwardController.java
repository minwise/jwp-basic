package minho.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;

public class ForwardController implements Controller {
	String forwardUrl;
	
	public ForwardController(String url) {
		forwardUrl = url;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forwardUrl;
	}

}
