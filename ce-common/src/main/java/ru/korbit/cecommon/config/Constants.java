package ru.korbit.cecommon.config;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Artur Belogur on 13.10.17.
 */
public abstract class Constants {

    private Constants() { }

    public static final String QUEUE_NAME = "ce-queue";

    public static final String IMAGE_DIR = "/ce-images/";
    public static final String IMAGE_BASE_URL = "http://localhost:8082/%s";

    public static URL getDefaultImage() {
        try {
            return new URL("https://www.technodoze.com/wp-content/uploads/2016/03/default-placeholder.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
