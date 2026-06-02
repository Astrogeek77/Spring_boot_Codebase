package com.abstractfactory.mutli_cloud_provision_system.aws;

import com.abstractfactory.mutli_cloud_provision_system.product.Compute;

public class AwsCompute implements Compute {
    @Override
    public String provisionCompute() {
        return "AWS EC2 Instance successfully provisioned.";
    }
}
