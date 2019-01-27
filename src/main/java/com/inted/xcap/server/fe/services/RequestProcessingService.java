package com.inted.xcap.server.fe.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inted.xcap.server.fe.XcapServer;
import com.inted.xcap.server.fe.services.DocumentService;
import com.inted.xcap.server.fe.models.AppUsage;
import com.inted.xcap.server.fe.models.AppUsageType;
import com.inted.xcap.server.fe.models.XcapDocument;
import com.inted.xcap.server.fe.util.XcapException;
import com.inted.xcap.server.fe.util.XcapOperation;

public class RequestProcessingService {

	private static final Logger LOG = LoggerFactory.getLogger(RequestProcessingService.class);
	 
	
	public Map<String, Object> processRequest(String xcapRequest, XcapOperation opr, String payload) throws XcapException, IOException {

		//default error result value

		boolean docRequest = isDocumentRequest(xcapRequest);
		
		if (opr == XcapOperation.ADD) {
			return doAdd(xcapRequest, docRequest, payload);
		}
		//else if(opr == XcapOperation.FETCH){
	//
		//}

		return null;
	}


	public String doFetch(String xcapRequest) throws XcapException, IOException {

			// check if it is conforms
			if (xcapRequest.endsWith("/")){
				
				throw new XcapException("The following should not end the document selector: /", HttpServletResponse.SC_BAD_REQUEST);
			}
			String docSelector[] = xcapRequest.split("/");
			LOG.info("Step A1");
			LOG.info("Selector Size: "+docSelector.length);
			String doc = new DocumentService().fetchDocument(docSelector);
			
			return doc;
		

	}
	
	public Map<String, Object> doAdd(String xcapRequest, boolean docRequest, String xml) throws XcapException, IOException {

		if (docRequest) {
			// check if it is conforms
			if (xcapRequest.endsWith("/")){
				
				throw new XcapException("The following should not end the document selector: /", HttpServletResponse.SC_BAD_REQUEST);
			}
			String docSelector[] = xcapRequest.split("/");

			XcapDocument doc = createDocument(docSelector, xml);
			

			
			Map<String, Object> result = new HashMap<String, Object>();

			result.put("isDocument", true);
			result.put("resource", doc);
			return result;
	

		}
		// if it is a node request
		else {
			String selector[] = xcapRequest.split("/~~/");

			String documentSelector;
			String nodeSelector;

			if (selector.length == 2) {

				documentSelector = selector[0];
				LOG.info("documentSelector: " + documentSelector);

				nodeSelector = selector[1];
				LOG.info("nodeSelector: " + nodeSelector);

			} else{
			
				throw new XcapException("Invalid XCAP query", HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		return null;
	}

	public boolean isDocumentRequest(String xcapRequest) {
		// Check its a document request
		if (xcapRequest.indexOf("~~") == -1) {
			LOG.error("No node selector present");
			return true;
		}
		return false;
	}

	public XcapDocument createDocument(String docSelector[], String xml) throws XcapException {

		String auid, documentTree, xuid, documentName;


		 if (docSelector.length == 6) {

                        LOG.info("1 = " + docSelector[0]);
                        LOG.info("2 = " + docSelector[1]);

                        LOG.info("3 = " + docSelector[2]);
                        auid = docSelector[2];

                        LOG.info("4 = " + docSelector[3]);
                        documentTree = docSelector[3];

                        LOG.info("5 = " + docSelector[4]);
                        xuid = docSelector[4];

                        LOG.info("6 = " + docSelector[5]);
                        documentName = docSelector[5];

			if (!(documentTree.equals("global") || documentTree.equals("users"))) {
			
				throw new XcapException("Invalid XCAP query: Scope not valid", HttpServletResponse.SC_BAD_REQUEST);
			}

			if (xml == null || xml.isEmpty()){
				
				throw new XcapException("Invalid XCAP query: No valid document detected!", HttpServletResponse.SC_BAD_REQUEST);
			}
				

			AppUsage appUsage = new AppUsageType(auid).getApp();
			XcapDocument doc = new XcapDocument(appUsage, documentTree, xuid, documentName, xml);

			return doc;
		}
		return null;
	}
	
	

}
