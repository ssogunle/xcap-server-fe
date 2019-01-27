package com.inted.xcap.server.fe.csdr;

import java.net.URI;
import java.util.List;

import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.request.retrieve.v4.RetrieveRequestFactory;
import org.apache.olingo.client.api.communication.response.ODataEntityCreateResponse;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;

import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.commons.api.domain.v4.ODataEntity;
import org.apache.olingo.commons.api.domain.v4.ODataEntitySet;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inted.xcap.server.fe.models.XcapDocument;

public class CsdrClientProcessor {

	private Logger LOG = LoggerFactory.getLogger(CsdrClientProcessor.class);

	private ODataClient client;
	private String serviceRoot;
	//private final String XCAP_NAMESPACE = "com.inted.csdr.xcap";
	private final String XCAP_NAMESPACE = "com.inted.csdr";
	
	public CsdrClientProcessor(ODataClient client, String serviceRoot) {

		this.client = client;
		this.serviceRoot = serviceRoot;
	}

	public List<ODataEntity> executeRequest(URI entitySetUri) {

		// LOG.info("URI :"+entitySetUri.toString());
		RetrieveRequestFactory rrf = client.getRetrieveRequestFactory();

		/// LOG.info("E1");
		ODataEntitySetRequest<ODataEntitySet> request = rrf.getEntitySetRequest(entitySetUri);
		request.setAccept("application/json;odata.metadata=minimal");

		// LOG.info("E2");
		ODataRetrieveResponse<ODataEntitySet> response = request.execute();

		// LOG.info("E3");
		ODataEntitySet retrievedEntitySet = response.getBody();

		return retrievedEntitySet.getEntities();
		
	}
	public ODataEntityCreateResponse<ODataEntity> addEntity(String entitySetName, String entityType, XcapDocument doc)
			throws Exception {

		LOG.info("Creating document entity..");

		// Creating EntitySet URI
		URI entitySetUri = client.newURIBuilder(serviceRoot).appendEntitySetSegment(entitySetName).build();

		// Creating OData Entity
		ODataEntity entity = client.getObjectFactory().newEntity(new FullQualifiedName(XCAP_NAMESPACE, entityType));

		// Add AUID
		entity.getProperties().add(client.getObjectFactory().newPrimitiveProperty("Auid",
				client.getObjectFactory().newPrimitiveValueBuilder().buildString(doc.getAppUsage().getAuid())));

		// Add XUI
		entity.getProperties().add(client.getObjectFactory().newPrimitiveProperty("Xui",
				client.getObjectFactory().newPrimitiveValueBuilder().buildString(doc.getXuid())));

		// Add Document Name
		entity.getProperties().add(client.getObjectFactory().newPrimitiveProperty("DocumentName",
				client.getObjectFactory().newPrimitiveValueBuilder().buildString(doc.getFileName())));

		// Add Document Data: XML Data
		entity.getProperties().add(client.getObjectFactory().newPrimitiveProperty("Data",
				client.getObjectFactory().newPrimitiveValueBuilder().buildString(doc.getXmlData())));

		// Add Update Status for Presence Watcher: Hypothetical for now...
		entity.getProperties().add(client.getObjectFactory().newPrimitiveProperty("isUpdated",
				client.getObjectFactory().newPrimitiveValueBuilder().buildBoolean(false)));

		// Create OData Service Request
		ODataEntityCreateRequest<ODataEntity> request = client.getCUDRequestFactory()
				.getEntityCreateRequest(entitySetUri, entity);

		request.setAccept("application/json;odata.metadata=minimal");

		ODataEntityCreateResponse<ODataEntity> response = request.execute();

		return response;

	}

	public ODataEntity fetchEntity() {

		return null;
	}
	

}
