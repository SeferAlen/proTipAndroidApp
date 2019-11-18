package com.example.protip.asyncTasks;

import com.example.protip.model.APIResponseLogin;

public interface AsyncLoginAPIResponse {

    void processFinished(final APIResponseLogin apiResponseLogin);
}
