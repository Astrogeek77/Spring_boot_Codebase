package com.gautam.document_publishing_workflow.Pattern;


import com.gautam.document_publishing_workflow.Interfaces.DocumentState;
import com.gautam.document_publishing_workflow.Model.Document;

public class DraftState implements DocumentState {

    @Override
    public String submitForReview(Document document) {
        document.setState(new UnderReviewState());
        return "Document submitted for review. It is now UNDER REVIEW.";
    }

    @Override
    public String approve(Document document) {
        return "Cannot approve a document that is still a DRAFT. Submit it for review first.";
    }

    @Override
    public String reject(Document document) {
        return "Cannot reject a DRAFT. It hasn't been submitted yet.";
    }
}
