package com.example.protip.model;

/**
 * Class representing data send by proTipServices API
 */
public class APIResponse {

    private String response;
    private int responseCode;

    /**
     * Instantiates a new Api response.
     *
     * @param response     the response
     * @param responseCode the response code
     */
    public APIResponse(String response, int responseCode) {
        this.response = response;
        this.responseCode = responseCode;
    }

    /**
     * Gets response.
     *
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * Sets response.
     *
     * @param response the response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Gets response code.
     *
     * @return the response code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Sets response code.
     *
     * @param responseCode the response code
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
