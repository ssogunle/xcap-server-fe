package com.inted.xcap.server.fe.models;

public class AppUsage {

	protected String appName;
	protected String auid;
	protected String defaultNamespaceName;
	protected String defaultNamespaceValue;
	protected String schemaLocation;
	protected String mimeType;

	public AppUsage(String appName, String defaultNamespaceName, String defaultNamespaceValue,
			String schemaLocation,   String auid, String mimeType) {

		this.appName = appName;
		this.auid = auid;
		this.defaultNamespaceName = defaultNamespaceName;
		this.defaultNamespaceValue = defaultNamespaceValue;
		this.schemaLocation = schemaLocation;
		this.mimeType = mimeType;

	}

	public String getAppName() {
		return appName;
	}

	public String getAuid() {
		return auid;
	}

	public String getDefaultNamespaceName() {
		return defaultNamespaceName;
	}

	public String getDefaultNamespaceValue() {
		return defaultNamespaceValue;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public String getMimeType() {
		return mimeType;
	}

}
