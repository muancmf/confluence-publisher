package com.github.muancmf.confluence.api;

import com.github.muancmf.confluence.model.Storage;
import com.github.muancmf.confluence.publisher.ConfluencePublisher;
import joptsimple.internal.Strings;
import kong.unirest.Unirest;

public class ContentBodyAPI extends BaseAPI {
    private static final String CONFLUENCE_CONTENT_BODY = "contentbody";

    public ContentBodyAPI(ConfluencePublisher publisher){
        super(publisher);
    }

    public Storage convertWikiMarkupToStorageFormat(Storage payload) {
        return authorizedRequestWithBody(Unirest.post(getContentBodyWith("convert/storage")))
                .body(payload)
                .asObject(Storage.class).getBody();

    }

    private String getContentBodyWith(String parameters) {
        return Strings.join(
                new String[]{
                        getURI(),
                        CONFLUENCE_CONTENT_BODY,
                        parameters
                }, SEPARATOR);
    }
}
