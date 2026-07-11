package com.gautam.document_publishing_workflow.Pattern;


import com.gautam.document_publishing_workflow.Interfaces.DocumentState;
import com.gautam.document_publishing_workflow.Model.Document;

public class PublishedState implements DocumentState {

    @Override
    public String submitForReview(Document document) {
        return "Cannot submit for review. Document is already PUBLISHED.";
    }

    @Override
    public String approve(Document document) {
        return "Document is already PUBLISHED.";
    }

    @Override
    public String reject(Document document) {
        return "Cannot reject a PUBLISHED document. You must unpublish it first.";
    }
}
