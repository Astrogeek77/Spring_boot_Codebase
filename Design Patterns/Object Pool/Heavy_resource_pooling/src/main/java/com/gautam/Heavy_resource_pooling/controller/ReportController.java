package com.gautam.Heavy_resource_pooling.controller;

import com.gautam.Heavy_resource_pooling.model.ExportEngine;
import com.gautam.Heavy_resource_pooling.pattern.ExportEnginePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ExportEnginePool enginePool;

    @Autowired
    public ReportController(ExportEnginePool enginePool) {
        this.enginePool = enginePool;
    }

    @GetMapping("/generate")
    public Map<String, Object> generateReport(@RequestParam String data) {
        ExportEngine engine = null;
        Map<String, Object> response = new HashMap<>();
        long startTime = System.currentTimeMillis();

        try {
            // 1. Borrow an engine from the pool
            engine = enginePool.borrowObject();

            // 2. Use the engine
            String result = engine.generateReport(data);

            response.put("status", "SUCCESS");
            response.put("message", result);
            response.put("engineUsed", engine.getEngineId());
        } catch (RuntimeException e) {
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
        } finally {
            // 3. ALWAYS return the object back to the pool in a finally block!
            if (engine != null) {
                enginePool.returnObject(engine);
            }
        }

        response.put("timeTakenMs", System.currentTimeMillis() - startTime);
        return response;
    }

    @GetMapping("/pool-status")
    public Map<String, Object> getPoolStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("maxCapacity", 3);
        status.put("totalObjectsCreated", enginePool.getTotalCreated());
        status.put("objectsCurrentlyInPool", enginePool.getAvailableCount());
        status.put("objectsCurrentlyInUse", enginePool.getTotalCreated() - enginePool.getAvailableCount());
        return status;
    }
}
