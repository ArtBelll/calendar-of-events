package ru.korbit.ceramblerkasse;

/**
 * Created by Artur Belogur on 16.10.17.
 */
public abstract class Environment {

    private Environment() {}

    // base_url = host/api_version/api_key/format
    private final static String BASE_URL = "%s/%s/%s/%s";

    public final static String PROXY = "http://188.225.9.247:8888";

    public final static String API_VERSION = "v2";
    public final static String API_KEY = "1a294004-f4db-47ee-a63e-4702cadbc621";
    public final static String FORMAT = "json";

    public static String host = "http://api.kassa.rambler.ru";

    public static String getBaseUrl() {
        return String.format(BASE_URL, host, API_VERSION, API_KEY, FORMAT);
    }

}
