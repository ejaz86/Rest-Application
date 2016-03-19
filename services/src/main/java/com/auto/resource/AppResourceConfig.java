package com.auto.resource;

import javax.json.stream.JsonGenerator;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.auto.resource.security.SecurityFilter;

public class AppResourceConfig extends ResourceConfig {
	public AppResourceConfig() {
		packages("com.auto.resource");
		register(RolesAllowedDynamicFeature.class);
		register(JacksonFeature.class);
		register(SecurityFilter.class);
		property(JsonGenerator.PRETTY_PRINTING, true);
	}
}
