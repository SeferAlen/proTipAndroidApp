package com.example.protip.asyncTasks;

import com.example.protip.model.APIResponse;

public interface AsyncLoginAPIResponse {

    void processFinished(final APIResponse apiResponse);
}
