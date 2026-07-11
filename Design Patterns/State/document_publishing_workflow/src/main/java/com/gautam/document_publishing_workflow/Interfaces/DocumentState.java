package com.gautam.document_publishing_workflow.Interfaces;


import com.gautam.document_publishing_workflow.Model.Document;

public interface DocumentState {
    String submitForReview(Document document);
    String approve(Document document);
    String reject(Document document);
}
