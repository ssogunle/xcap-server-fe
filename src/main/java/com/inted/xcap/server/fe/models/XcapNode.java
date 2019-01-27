package com.inted.xcap.server.fe.models;

public class XcapNode {

	//private Node parent;
	
	private XcapDocument document;
	
	private String nodeSelector;
	
	/* XCAP query */
	protected String xcapQuery;

	/* Complete XPATH expression */
	protected String xpathExpression;

	/* Last Path Segment */
	//protected String lastPathSegment;
	
	
	public XcapNode(XcapDocument document,String nodeSelector, String xcapQuery, String xpathExpression){
		this.document = document;
		this.nodeSelector = nodeSelector;
		this.xcapQuery = xcapQuery;
		this.xpathExpression = xpathExpression;
	}

	public XcapDocument getDocument() {
		return document;
	}

	public String getNodeSelector() {
		return nodeSelector;
	}

	public String getXcapQuery() {
		return xcapQuery;
	}

	public String getXpathExpression() {
		return xpathExpression;
	}
	
	
}
