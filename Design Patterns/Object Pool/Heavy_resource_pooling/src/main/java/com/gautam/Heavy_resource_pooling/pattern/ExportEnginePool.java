package com.gautam.Heavy_resource_pooling.pattern;

import com.gautam.Heavy_resource_pooling.model.ExportEngine;
import org.springframework.stereotype.Component;

@Component
public class ExportEnginePool extends ObjectPool<ExportEngine> {

    // Set max pool size to 3 for demonstration purposes
    public ExportEnginePool() {
        super(3);
    }

    @Override
    protected ExportEngine create() {
        return new ExportEngine();
    }
}
