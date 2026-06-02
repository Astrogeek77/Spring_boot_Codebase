package com.abstractfactory.mutli_cloud_provision_system.aws;

import com.abstractfactory.mutli_cloud_provision_system.product.Storage;

public class AwsStorage implements Storage {
    @Override
    public String provisionStorage() {
        return "AWS S3 Bucket successfully created.";
    }
}