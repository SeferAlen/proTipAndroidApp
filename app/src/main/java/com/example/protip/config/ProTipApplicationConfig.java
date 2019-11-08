package com.example.protip.config;

/**
 * Class containing basic configuration data for app
 */
public class ProTipApplicationConfig {
    private static final String proTipServicesURL = "https://protipservices.herokuapp.com/login";

    /**
     * Gets proTipServices url
     *
     * @return proTipServicesURL {@link String} the proTipServices url
     */
    public static String getProTipServicesURL() {
        return proTipServicesURL;
    }
}
