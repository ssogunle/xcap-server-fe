package com.inted.xcap.server.fe.csdr;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.olingo.client.api.communication.response.ODataEntityCreateResponse;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inted.xcap.server.fe.models.XcapDocument;

public class CsdrClient {

	private static final Logger LOG = LoggerFactory.getLogger(CsdrClient.class);

	// CSDR Url
	private static final String serviceRoot = "http://localhost:8080/csdr/svc";

	private static final String docEntitySetName = "XcapDocuments";

	private static final String docEntityType = "XcapDocument";

	private ODataClient client;

	private CsdrClientProcessor ccp;

	public CsdrClient() {

		client = ODataClientFactory.getV4();
		ccp = new CsdrClientProcessor(client, serviceRoot);

	}

	public ODataEntity create(XcapDocument document) throws Exception {

		ODataEntityCreateResponse<ODataEntity> response = ccp.addEntity(docEntitySetName, docEntityType, document);

		if (response.getStatusCode() == 201) {

			return response.getBody();
		}
		return null;
	}

	public ODataEntity fetch(String auid, String xuid, String documentName) {

		URI entitySetUri = client.newURIBuilder(serviceRoot).appendEntitySetSegment("XcapDocuments").build();
		// .filter("Auid eq '" + auid + "' and Xui eq '" + xuid + "' and DocumentName eq '" + documentName + "'").build();
		// .filter("DocumentName eq '" + documentName + "'").build();
		List<ODataEntity> set = ccp.executeRequest(entitySetUri);
		LOG.info("XCAP docs size: "+set.size());
		for (ODataEntity et : set) {

			if (et.getProperty("Auid") != null && et.getProperty("Xui") != null
					&& et.getProperty("DocumentName") != null) {

				if (String.valueOf(et.getProperty("Auid").getValue()).equals(auid)
						&& String.valueOf(et.getProperty("Xui").getValue()).equals(xuid)
						&& String.valueOf(et.getProperty("DocumentName").getValue()).equals(documentName)) {
					return et;
				}
			}

		}
		return null;

	}
}
