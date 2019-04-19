package com.protosinc.rspark;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.DoneableConfigMap;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;

@WebListener
public class InitializerListener implements ServletContextListener {
	private KubernetesClient k8sclient;		
	private String namespaceName, jobTemplateName;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// should read this from environment variables
		namespaceName = System.getenv("DEPLOY_NAMESPACE");
		if (namespaceName == null) {
			namespaceName = "default";
		}
		jobTemplateName = System.getenv("DEPLOY_JOB_TEMPLATE");
		if (jobTemplateName == null) {
			throw new IllegalArgumentException("k8s job template not specified");
		}
		//open the client
		k8sclient = new DefaultKubernetesClient();
		
		//read job resource
		ConfigMap cfgMap =  
				 k8sclient.configMaps()
				 .inNamespace(namespaceName)
				 .withName(jobTemplateName).get();
		if (cfgMap == null) {
			throw new IllegalArgumentException("failed to load job template configmap");
		}
		System.out.println("cm kind = " + cfgMap.getKind());
		
		//get list of namespaces and print
		List<String> names = k8sclient.namespaces().list().getItems().stream()
				.map(s -> s.getMetadata().getName())
				.collect(Collectors.toList());
		System.out.println(names);
		System.out.println("context initiailized");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		k8sclient.close();
	}

	public String getNamespaceName() {
		return namespaceName;
	}

	public void setNamespaceName(String namespaceName) {
		this.namespaceName = namespaceName;
	}

}
