package com.abstractfactory.mutli_cloud_provision_system.factory;

import org.springframework.stereotype.Component;

import com.abstractfactory.mutli_cloud_provision_system.gcp.GcpCompute;
import com.abstractfactory.mutli_cloud_provision_system.gcp.GcpStorage;
import com.abstractfactory.mutli_cloud_provision_system.product.Compute;
import com.abstractfactory.mutli_cloud_provision_system.product.Storage;

@Component("gcpFactory")
public class GcpFactory implements CloudFactory {

    @Override
    public Compute createCompute() {
        return new GcpCompute();
    }

    @Override
    public Storage createStorage() {
        return new GcpStorage();
    }
}
