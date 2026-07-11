package com.gautam.document_template_system.model;


public class Report extends Document {
    public Report() {
        setType("Report");
        setTitle("Standard Report Template");
        setContent("Executive Summary: \n[Insert Data Here]");
    }
}
