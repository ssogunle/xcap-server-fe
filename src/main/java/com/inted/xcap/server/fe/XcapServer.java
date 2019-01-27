package com.inted.xcap.server.fe;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inted.xcap.server.fe.models.XcapDocument;
import com.inted.xcap.server.fe.services.DocumentService;

import com.inted.xcap.server.fe.services.RequestProcessingService;
import com.inted.xcap.server.fe.util.XcapException;
import com.inted.xcap.server.fe.util.XcapOperation;

/**
 * 
 * @author Segun Sogunle
 *
 *         The main class for the Front-End application
 */

public class XcapServer extends HttpServlet {

	Logger LOG = LoggerFactory.getLogger(XcapServer.class);
	private Map<String, Object> result;

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String xcapQuery = req.getPathInfo();
		String contextPath = req.getContextPath();
		String pathTranslated = req.getPathTranslated();
		//String payload = org.apache.commons.io.IOUtils.toString(req.getReader());
		
		if (xcapQuery == null || xcapQuery.isEmpty()) {
			LOG.error("Document Selector not found!");
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Document Selector not found!");
			return;
		}
		String res = null;
	
		// LOG.info("xcapQuery: "+xcapQuery);
	
		try {
			LOG.info("Step A");
			res = new RequestProcessingService().doFetch(xcapQuery);
			LOG.info("Step B");
			if(res!=null){
				LOG.info("Step C");
			resp.setContentType("application/rls-services+xml");
			resp.getWriter().append(res);
			resp.setStatus(HttpServletResponse.SC_OK);
			}
			else{
				LOG.info("Step D");
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		}catch(XcapException ex){
			LOG.error("Error with request : " + ex.getErrorMessage());
			resp.sendError(ex.getErrorCode(), ex.getErrorMessage());
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String xcapQuery = req.getPathInfo();
		String contextPath = req.getContextPath();
		String pathTranslated = req.getPathTranslated();
		String payload = org.apache.commons.io.IOUtils.toString(req.getReader());
		// String xcapQuery = req.getQueryString();

		// if(xcapQuery == null || xcapQuery.isEmpty()){
		// LOG.error("No XCAP query detected!");
		// resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No XCAP query
		// detected!");
		// return;
		// }

		if (xcapQuery == null || xcapQuery.isEmpty()) {
			LOG.error("Document Selector not found!");
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Document Selector not found!");
			return;
		}

		LOG.info("Here I am ....");
		LOG.info("xcapQuery: " + xcapQuery);
		LOG.info("contextPath: " + contextPath);
		LOG.info("pathTranslated: " + pathTranslated);
		// LOG.info("xcapQuery: "+xcapQuery);

		try {
			result = new RequestProcessingService().processRequest(xcapQuery, XcapOperation.ADD, payload);

			if (result != null) {

				boolean isDocument = (boolean) result.get("isDocument");

				if (isDocument) {

					DocumentService ds = new DocumentService();
					XcapDocument doc = (XcapDocument) result.get("resource");

					LOG.info(doc.toString());

					boolean ans = new DocumentService().addDocument(doc);

					if (ans) {
						resp.setStatus(HttpServletResponse.SC_CREATED);
						return;
					} else {
						resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Document was not added!");
						return;
					}
				}
				// For Node PUT OPERATIONS
				else {

				}
			}

		} catch (XcapException ex) {
			LOG.error("Error with request : " + ex.getErrorMessage());
			resp.sendError(ex.getErrorCode(), ex.getErrorMessage());
		}
		
	}

}
