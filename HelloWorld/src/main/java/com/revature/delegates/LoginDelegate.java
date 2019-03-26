package com.revature.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.beans.User;
import com.revature.data.UserDAO;
import com.revature.data.UserOracle;

public class LoginDelegate implements FrontControllerDelegate {
	private static Logger log = Logger.getLogger(LoginDelegate.class);
	private UserDAO ud = new UserOracle();
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace("Login Delegate");
		String switchString = (String) req.getAttribute("uri-path");
		switch(switchString) {
		case "login": login(req, resp); break;
		case "logout": logout(req, resp); break;
		default: resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
		switch(method) {
		case "GET": getLogin(req, resp); break;
		case "POST": postLogin(req, resp); break;
		default: resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED); break;
		}
	}
	private void postLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = req.getParameter("user");
		String password = req.getParameter("pass");
		User u = ud.getUser(username, password);
		if(u==null) {
			resp.sendRedirect("login");
		} else {
			req.getSession().setAttribute("user", u);
			resp.sendRedirect("home");
		}
	}
	private void getLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if(session.getAttribute("user")==null) {
			req.getRequestDispatcher("static/login.html").forward(req, resp);
		} else {
			resp.sendRedirect("home");
		}
	}
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(!"POST".equals(req.getMethod())) {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED); //405
			return;
		}
		req.getSession().invalidate();
		resp.sendRedirect("login");
	}

}
