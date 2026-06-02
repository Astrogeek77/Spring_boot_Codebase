package com.abstractfactory.mutli_cloud_provision_system.factory;

import com.abstractfactory.mutli_cloud_provision_system.product.Compute;
import com.abstractfactory.mutli_cloud_provision_system.product.Storage;

public interface CloudFactory {
    Compute createCompute();

    Storage createStorage();
}
