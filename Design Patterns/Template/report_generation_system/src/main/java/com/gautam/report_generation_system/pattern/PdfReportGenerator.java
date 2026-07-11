package com.gautam.report_generation_system.pattern;


import org.springframework.stereotype.Component;

@Component("pdfReportGenerator")
public class PdfReportGenerator extends ReportGenerator {

    @Override
    protected String fetchData() {
        return "Step 2 (PDF): Executing complex SQL queries to fetch PDF data.";
    }

    @Override
    protected String formatData() {
        return "Step 3 (PDF): Formatting data into a strict PDF page layout with headers and footers.";
    }

    @Override
    protected String exportReport() {
        return "Step 4 (PDF): Exporting binary stream as a .pdf file.";
    }
}
