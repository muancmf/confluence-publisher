package com.github.muancmf.confluence.api;

import com.github.muancmf.confluence.publisher.ConfluencePublisher;
import joptsimple.internal.Strings;
import kong.unirest.HttpRequest;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseAPI {
    protected ConfluencePublisher publisher;

    private static final String SCHEME = "https";
    private static final String CONFLUENCE_API = "wiki/rest/api";
    protected static final String SEPARATOR = "/";

    protected BaseAPI(ConfluencePublisher publisher) {
        this.publisher = publisher;
    }

    protected  <T extends HttpRequest<T>> T authorizedRequestWithBody(T request) {
        return authorizedRequest(request)
                .header("Content-Type", "application/json");
    }

    protected <T extends HttpRequest<T>> T authorizedRequest(T request) {
        return authorized(request)
                .header("Accept", "application/json");
    }

    private <T extends HttpRequest<T>> T authorized(T request) {
        return request
                .headerReplace("Authorization", "Basic " + publisher.getAuthentication());
    }

    protected String getURI() {
        try {
            return Strings.join(
                    new String[]{
                            new URL(SCHEME, publisher.getDomainName(), Strings.EMPTY).toString(),
                            CONFLUENCE_API
                    }, SEPARATOR);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
