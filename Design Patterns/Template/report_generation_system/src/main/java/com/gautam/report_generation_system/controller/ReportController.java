package com.gautam.report_generation_system.controller;

import com.gautam.report_generation_system.pattern.ReportGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    // Spring automatically injects all beans of type ReportGenerator into this Map!
    // The keys will be the bean names (e.g., "pdfReportGenerator", "htmlReportGenerator").
    private final Map<String, ReportGenerator> reportGenerators;

    @Autowired
    public ReportController(Map<String, ReportGenerator> reportGenerators) {
        this.reportGenerators = reportGenerators;
    }

    @GetMapping("/generate/{type}")
    public List<String> generateReport(@PathVariable String type, @RequestParam String name) {
        // Construct the bean name based on the path variable (e.g., "pdf" -> "pdfReportGenerator")
        String beanName = type.toLowerCase() + "ReportGenerator";

        ReportGenerator generator = reportGenerators.get(beanName);

        if (generator == null) {
            throw new IllegalArgumentException("Unsupported report type: " + type);
        }

        // We only call the Template Method. The internal sequence is handled automatically!
        return generator.generateReport(name);
    }
}
