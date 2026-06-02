package com.abstractfactory.mutli_cloud_provision_system.factory;

import org.springframework.stereotype.Component;

import com.abstractfactory.mutli_cloud_provision_system.aws.AwsCompute;
import com.abstractfactory.mutli_cloud_provision_system.aws.AwsStorage;
import com.abstractfactory.mutli_cloud_provision_system.product.Compute;
import com.abstractfactory.mutli_cloud_provision_system.product.Storage;

@Component("awsFactory")
public class AwsFactory implements CloudFactory {

    @Override
    public Compute createCompute() {
        return new AwsCompute();
    }

    @Override
    public Storage createStorage() {
        return new AwsStorage();
    }

}
