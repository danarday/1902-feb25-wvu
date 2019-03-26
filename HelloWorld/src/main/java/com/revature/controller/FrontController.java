package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.revature.delegates.FrontControllerDelegate;

public class FrontController extends DefaultServlet {
	private Logger log = Logger.getLogger(FrontController.class);
	private RequestDispatcher rd = new RequestDispatcher();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		log.trace(req.getRequestURI());
		log.trace(req.getContextPath());
		
		if(req.getRequestURI().substring(req.getContextPath().length())
				.startsWith("/static")) {
			log.trace("static!");

			super.doGet(req, resp);
		}	else {
			log.trace("not static!");
			FrontControllerDelegate delegate = rd.dispatch(req, resp);
			if(delegate==null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				delegate.process(req, resp);
			}
		}
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		this.doGet(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
