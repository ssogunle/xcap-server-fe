package com.inted.xcap.server.fe.services;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inted.xcap.server.fe.csdr.CsdrClient;
import com.inted.xcap.server.fe.models.XcapDocument;
import com.inted.xcap.server.fe.util.XcapException;

public class DocumentService {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);
	CsdrClient client = new CsdrClient();

	public boolean addDocument(XcapDocument doc) {

		ODataEntity et = null;
		try {
			et = client.create(doc);
		} catch (Exception ex) {
			LOG.error("Error Occured while creating data on CSDR: " + ex);
			ex.printStackTrace();
		}

		return (et == null) ? false : true;
	}

	public void deleteDocument(XcapDocument doc) {

	}

	public String fetchDocument(String docSelector[]) throws XcapException, IOException{
		LOG.info("Step A2");
		String auid, documentTree, xuid, documentName;
		String docData = null;
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
			
			try {
				//et = client.fetch(auid,documentTree, xuid, documentName);
				ODataEntity et = client.fetch(auid, xuid, documentName);
				
				if(et!=null){
					LOG.info("Step A*");
				docData =	et.getProperty("Data") == null ? null
							: String.valueOf(et.getProperty("Data").getValue());
				}
		
			} catch (Exception ex) {
				LOG.error("Error Occured while creating data on CSDR: " + ex);
				ex.printStackTrace();
			}

		}
		LOG.info("Step A4");
		return docData;
	}
}
