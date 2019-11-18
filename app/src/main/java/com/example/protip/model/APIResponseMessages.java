package com.example.protip.model;

import java.util.List;

/**
 * Class representing data send by proTipServices API when getting messages is performed
 */
public class APIResponseMessages {

    private List<ReceivedMessages> receivedMessagesList;
    private int responseCode;

    /**
     * Instantiates a new Api response messages.
     *
     * @param receivedMessagesList the received messages list
     * @param responseCode         the response code
     */
    public APIResponseMessages(List<ReceivedMessages> receivedMessagesList, int responseCode) {
        this.receivedMessagesList = receivedMessagesList;
        this.responseCode = responseCode;
    }

    /**
     * Gets received messages list.
     *
     * @return the received messages list
     */
    public List<ReceivedMessages> getReceivedMessagesList() {
        return receivedMessagesList;
    }

    /**
     * Sets received messages list.
     *
     * @param receivedMessagesList the received messages list
     */
    public void setReceivedMessagesList(List<ReceivedMessages> receivedMessagesList) {
        this.receivedMessagesList = receivedMessagesList;
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
