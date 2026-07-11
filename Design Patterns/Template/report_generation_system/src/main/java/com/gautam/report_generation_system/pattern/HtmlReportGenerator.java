package com.gautam.report_generation_system.pattern;

import org.springframework.stereotype.Component;

@Component("htmlReportGenerator")
public class HtmlReportGenerator extends ReportGenerator {

    @Override
    protected String fetchData() {
        return "Step 2 (HTML): Fetching data from NoSQL database for web presentation.";
    }

    @Override
    protected String formatData() {
        return "Step 3 (HTML): Formatting data into responsive HTML tables and CSS grids.";
    }

    @Override
    protected String exportReport() {
        return "Step 4 (HTML): Exporting raw string as an .html file.";
    }
}
