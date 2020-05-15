package com.wergnet.wergnetoil.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wergnet.wergnetoil.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent>{
	
	@Override
	public void onApplicationEvent(ResourceCreatedEvent resourceCriatedEvent) {
		HttpServletResponse response = resourceCriatedEvent.getResponse();
		Long code = resourceCriatedEvent.getcode();
		
		addHeaderLocation(response, code);
	}

	private void addHeaderLocation(HttpServletResponse response, Long code) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
				.buildAndExpand(code).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
