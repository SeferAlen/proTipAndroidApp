package com.example.protip.config;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class containing basic configuration data for app
 */
public class ProTipApplicationConfig {
    private static final String proTipServicesLoginURL = "https://protipservices.herokuapp.com/login";
    private static final String proTipServicesGetMessagesURL = "https://protipservices.herokuapp.com/message/messages";
    private static final String proTipServicesLocalhostURL = "http://10.0.2.2:9090/login";
    private static final String proTipServicesTestURL = "http://192.168.0.22:9090/login";
    private static final String proTipServicesTest2URL = "http://192.168.0.22:9090/message/messages";
    private static final String rabbitMQHost = "192.168.0.22";
    private static final String rabbitMQUsername = "androidUser";
    private static final String rabbitMQPassword = "androidUser";
    private static final String rabbitMQQueue = "proTipServicesQueueChat";
    private static final String preferencesName = "ProTipSharedPreferences";
    private static final int rabbitMQPort = 5672;

    /**
     * Gets proTipServicesLogin url
     *
     * @return proTipServicesLoginURL {@link String} the proTipServicesLogin url
     */
    public static String getProTipServicesLoginURL() {
        return proTipServicesLoginURL;
    }

    /**
     * Gets proTipServicesGetMessages url
     *
     * @return proTipServicesGetMessagesURL {@link String} the proTipServicesGetMessages url
     */
    public static String getProTipServicesGetMessagesURL() {
        return proTipServicesGetMessagesURL;
    }

    /**
     * Gets shared preferences name
     *
     * @return preferencesName {@link String} the preferencesName
     */
    public static String getPreferencesName() {
        return preferencesName;
    }

    /**
     * Gets proTipServices localhost url
     *
     * @return proTipServicesLocalhostURL {@link String} the proTipServices localhost url
     */
    public static String getProTipServicesLocalhostURL() {
        return proTipServicesLocalhostURL;
    }

    /**
     * Gets proTipServices testing url
     *
     * @return proTipServicesTestURL {@link String} the proTipServices test url
     */
    public static String getProTipServicesTestURL() {
        return proTipServicesTestURL;
    }

    /**
     * Gets proTipServices testing url
     *
     * @return proTipServicesTest2URL {@link String} the proTipServices test2 url
     */
    public static String getProTipServicesTest2URL() {
        return proTipServicesTest2URL;
    }

    /**
     * Gets rabbit mq host
     *
     * @return rabbitMqHost {@link String} the rabbitMq host IP address
     */
    public static String getRabbitMQHost() {
        return rabbitMQHost;
    }

    /**
     * Gets rabbit mq port.
     *
     * @return rabbitMQPort {@link int} the rabbitMq port
     */
    public static int getRabbitMQPort() {
        return rabbitMQPort;
    }

    /**
     * Gets rabbit mq username.
     *
     * @return rabbitMQUsername {@link String} the rabbitMq username
     */
    public static String getRabbitMQUsername() {
        return rabbitMQUsername;
    }

    /**
     * Gets rabbit mq password.
     *
     * @return rabbitMQPassword {@link String} the rabbitMq password
     */
    public static String getRabbitMQPassword() {
        return rabbitMQPassword;
    }

    /**
     * Gets rabbit mq queue.
     *
     * @return rabbitMQQueue {@link String} the rabbitMq queue name
     */
    public static String getRabbitMQQueue() {
        return rabbitMQQueue;
    }
}
