package com.abstractfactory.mutli_cloud_provision_system.gcp;

import com.abstractfactory.mutli_cloud_provision_system.product.Compute;

public class GcpCompute implements Compute {
    @Override
    public String provisionCompute() {
        return "GCP Compute Engine instance successfully provisioned.";
    }
}
