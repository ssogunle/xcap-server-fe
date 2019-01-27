package com.inted.xcap.server.fe.models;

import com.inted.xcap.server.fe.models.AppUsage;

public class AppUsageType {

	AppUsage appUsage = null;

	public AppUsageType(String auid) {

		appUsage = createAppUsage(auid);
	}

	public AppUsage getApp() {
		return this.appUsage;
	}

	private AppUsage createAppUsage(String auid) {

		AppUsage appUsage = null;

		switch (auid) {

		case "presence-rules":
			appUsage = new AppUsage("presence-rules", "default_namespace", "urn:ietf:params:xml:ns:pres-rules",
					"presencerules.xsd", "pres-rules", "\"" + "application/auth-policy+xml" + "\"");
			break;
		case "resource-lists":
			appUsage = new AppUsage("resource-lists", "def", "urn:ietf:params:xml:ns:resource-lists", "rlschema.xsd",
					"resource-lists", "\"" + "application/resource-lists+xml" + "\"");
			break;
			
		case "supl":
			appUsage = new AppUsage("supl", "def", "urn:ietf:params:xml:ns:supl", "suplchema.xsd",
					"supl", "\"" + "application/supl+xml" + "\"");
			break;
			
		case "capdiscovery":
			appUsage = new AppUsage("supl", "def", "urn:ietf:params:xml:ns:capdiscovery", "capdiscoverychema.xsd",
					"capdiscovery", "\"" + "application/capdiscovery+xml" + "\"");
			break;
			
		case "cpm":
			appUsage = new AppUsage("cpm", "def", "urn:ietf:params:xml:ns:cpm", "cpmchema.xsd",
					"cpm", "\"" + "application/cpm+xml" + "\"");
			break;
			
		case "rcsconfig":
			appUsage = new AppUsage("rcsconfig", "def", "urn:ietf:params:xml:ns:rcsconfig", "rcsconfigchema.xsd",
					"rcsconfig", "\"" + "application/rcsconfig+xml" + "\"");
			break;
		}

		return appUsage;
	}

}
