package com.gautam.document_publishing_workflow.Model;

import com.gautam.document_publishing_workflow.Pattern.DraftState;
import com.gautam.document_publishing_workflow.Interfaces.DocumentState;

public class Document {
    private String content;
    private DocumentState state;

    public Document(String content) {
        this.content = content;
        // A new document always starts as a Draft
        this.state = new DraftState();
    }

    public void setState(DocumentState state) {
        this.state = state;
    }

    public String getStateName() {
        return this.state.getClass().getSimpleName();
    }

    // --- Delegate actions to the current state ---

    public String submitForReview() {
        return state.submitForReview(this);
    }

    public String approve() {
        return state.approve(this);
    }

    public String reject() {
        return state.reject(this);
    }
}
