package com.abstractfactory.mutli_cloud_provision_system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abstractfactory.mutli_cloud_provision_system.factory.CloudFactory;
import com.abstractfactory.mutli_cloud_provision_system.product.Compute;
import com.abstractfactory.mutli_cloud_provision_system.product.Storage;

@RestController
public class ProvisioningController {

    // Spring automatically injects all beans implementing CloudFactory into this
    // map!
    // The key is the bean name (e.g., "awsFactory", "gcpFactory").
    @Autowired
    private Map<String, CloudFactory> cloudFactories;

    @GetMapping("/api/cloud/provision")
    public List<String> provisionInfrastructure(@RequestParam String provider) {

        List<String> logs = new ArrayList<>();
        String factoryBeanName = provider.toLowerCase() + "Factory";

        // 1. Get the correct factory family at runtime
        CloudFactory factory = cloudFactories.get(factoryBeanName);

        if (factory == null) {
            logs.add("Error: Invalid Cloud Provider selected.");
            return logs;
        }

        // 2. The Client interacts ONLY with abstract interfaces.
        // It has no idea if it's creating AWS or GCP classes.
        Compute computeNode = factory.createCompute();
        Storage storageNode = factory.createStorage();

        // 3. Execute
        logs.add(computeNode.provisionCompute());
        logs.add(storageNode.provisionStorage());

        return logs;
    }
}
