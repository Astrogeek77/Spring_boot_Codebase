package com.gautam.report_generation_system.pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class ReportGenerator {

    // The Template Method: Defines the exact skeleton of the algorithm.
    // It is 'final' so subclasses cannot override the workflow sequence.
    public final List<String> generateReport(String reportName) {
        List<String> executionSteps = new ArrayList<>();

        executionSteps.add("--- Starting generation for: " + reportName + " ---");
        executionSteps.add(authenticate()); // Common step
        executionSteps.add(fetchData());    // Subclass specific
        executionSteps.add(formatData());   // Subclass specific
        executionSteps.add(exportReport()); // Subclass specific
        executionSteps.add("--- Report Generation Complete ---");

        return executionSteps;
    }

    // A concrete method shared by all subclasses
    private String authenticate() {
        return "Step 1: Authenticated user credentials successfully.";
    }

    // Abstract methods that subclasses MUST implement
    protected abstract String fetchData();
    protected abstract String formatData();
    protected abstract String exportReport();
}
