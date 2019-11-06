package com.example.protip.config;

/**
 * Class containing basic configuration data for app
 */
public class ProTipApplicationConfig {
    private static final String proTipServicesURL = "http://192.168.0.22:9090/login";

    /**
     * Gets proTipServices url
     *
     * @return the proTipServices url
     */
    public static String getProTipServicesURL() {
        return proTipServicesURL;
    }
}
