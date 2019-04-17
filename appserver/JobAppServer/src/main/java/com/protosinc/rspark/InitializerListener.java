package com.protosinc.rspark;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

@WebListener
public class InitializerListener implements ServletContextListener {
	private KubernetesClient k8sclient;
	private String namespaceName;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// should read this from environment variables
		namespaceName = System.getenv("DEPLOY_NAMESPACE");
		if (namespaceName == null) {
			namespaceName = "default";
		}
		
		k8sclient = new DefaultKubernetesClient();
		List<String> names = k8sclient.namespaces().list().getItems().stream()
				.map(s -> s.getMetadata().getName())
				.collect(Collectors.toList());
		System.out.println(names);
		System.out.println("context initiailized");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		

	}

	public String getNamespaceName() {
		return namespaceName;
	}

	public void setNamespaceName(String namespaceName) {
		this.namespaceName = namespaceName;
	}

}
