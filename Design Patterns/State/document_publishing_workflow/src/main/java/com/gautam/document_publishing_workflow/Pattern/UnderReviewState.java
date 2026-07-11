package com.gautam.document_publishing_workflow.Pattern;


import com.gautam.document_publishing_workflow.Interfaces.DocumentState;
import com.gautam.document_publishing_workflow.Model.Document;

public class UnderReviewState implements DocumentState {

    @Override
    public String submitForReview(Document document) {
        return "Document is already UNDER REVIEW.";
    }

    @Override
    public String approve(Document document) {
        document.setState(new PublishedState());
        return "Document approved! It is now PUBLISHED.";
    }

    @Override
    public String reject(Document document) {
        document.setState(new DraftState());
        return "Document rejected. It has been sent back to DRAFT state.";
    }
}
