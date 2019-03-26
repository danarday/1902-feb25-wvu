package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.delegates.FrontControllerDelegate;
import com.revature.delegates.HomeDelegate;
import com.revature.delegates.LoginDelegate;

public class RequestDispatcher {
	private Logger log = Logger.getLogger(RequestDispatcher.class);
	private FrontControllerDelegate hd = new HomeDelegate();
	private FrontControllerDelegate ld = new LoginDelegate();

	public FrontControllerDelegate dispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace("Request Delegate is handling request: "+req.getRequestURI());
		// /FrontController/home/hi
		String switchString = req.getRequestURI()
				.substring(req.getContextPath().length()+1);
		// home/hi
		log.trace(switchString);
		if(switchString.indexOf("/")>0) {
			switchString = switchString.substring(0,switchString.indexOf("/"));
		}
		// home
		log.trace(switchString);
		req.setAttribute("uri-path", switchString);
		switch(switchString) {
		case "home": return hd;
		case "login": return ld;
		case "logout": return ld;
		default: return null;
		}
	}
}
