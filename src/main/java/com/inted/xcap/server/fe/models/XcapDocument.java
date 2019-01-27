package com.inted.xcap.server.fe.models;

public class XcapDocument {

	private AppUsage appUsage;

	// Path of the Document Selector
	private String documentSelector;

	// Document Tree : Either "users" or "global"
	private String documentTree;

	// File name of the XCAP document
	private String fileName;

	// XCAP User identifier
	private String xuid;
	
	private String xmlData;

	public XcapDocument(AppUsage appUsage, String documentTree, String xuid, String fileName, String xmlData) {

		this.appUsage = appUsage;
		this.documentTree = documentTree;
		this.xuid = xuid;
		this.fileName = fileName;
		this.xmlData = xmlData;
	}

	public String getXmlData() {
		return xmlData;
	}

	public AppUsage getAppUsage() {
		return appUsage;
	}

	public String getDocumentSelector() {
		return documentSelector;
	}

	public String getDocumentTree() {
		return documentTree;
	}

	public String getFileName() {
		return fileName;
	}

	public String getXuid() {
		return xuid;
	}

	@Override
	public String toString() {
		return "XcapDocument [appUsage=" + appUsage + ", documentSelector=" + documentSelector + ", documentTree="
				+ documentTree + ", fileName=" + fileName + ", xuid=" + xuid + ", xmlData=" + xmlData + "]";
	}

}
