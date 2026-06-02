package com.abstractfactory.mutli_cloud_provision_system.gcp;

import com.abstractfactory.mutli_cloud_provision_system.product.Storage;

public class GcpStorage implements Storage {
    @Override
    public String provisionStorage() {
        return "GCP Cloud Storage bucket successfully created.";
    }
}
