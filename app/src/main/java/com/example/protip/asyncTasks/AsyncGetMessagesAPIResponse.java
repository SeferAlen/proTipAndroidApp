package com.example.protip.asyncTasks;

import com.example.protip.model.APIResponseMessages;

public interface AsyncGetMessagesAPIResponse {

    void processFinished(final APIResponseMessages apiResponseMessages);
}
