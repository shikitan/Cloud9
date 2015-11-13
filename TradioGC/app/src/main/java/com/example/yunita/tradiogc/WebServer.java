package com.example.yunita.tradiogc;

public class WebServer {
    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t09/inv/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t09/inv/_search?size=1000000";

    public WebServer() {

    }

    /**
     * Gets the resource url.
     *
     * @return String
     */
    public String getResourceUrl() {
        return RESOURCE_URL;
    }

    /**
     * Gets the search url.
     *
     * @return String
     */
    public String getSearchUrl() {
        return SEARCH_URL;
    }

}
