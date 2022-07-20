package com.example.demo.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {

    private DiscoveryClient discoveryClient;

    // organizationId를 받아서
    public Organization getOrganization(String organizationId){
        RestTemplate restTemplate = new RestTemplate();

        // 유레카에 등록된 "organizationservice"
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        return null;
    }

}
