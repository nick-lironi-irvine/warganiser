package org.warganiser.server.resources;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JacksonFeature implements Feature {

	private static final ObjectMapper mapper = new ObjectMapper() {
		private static final long serialVersionUID = 1L;
		{
			registerModule(new JodaModule());

			// We want ISO dates, not Unix timestamps!:
			configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		}
	};

	private static final JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider() {
		{
			setMapper(mapper);
		}
	};

	@Override
	public boolean configure(FeatureContext context) {
		context.register(provider);
		return true;
	}
}
