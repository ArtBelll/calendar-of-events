package ru.korbit.cecommon.packet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Artur Belogur on 16.10.17.
 */
@Slf4j
public class BaseUrlRestTemplate extends RestTemplate {

    private String baseUrl = "";

    public BaseUrlRestTemplate(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod httpMethod, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {
        try {
            return super.doExecute(new URI(baseUrl + url.toString()) , httpMethod,
                    requestCallback, responseExtractor);
        } catch (URISyntaxException e) {
            log.error("Not correct URL", e);
        }

        return null;
    }
}
