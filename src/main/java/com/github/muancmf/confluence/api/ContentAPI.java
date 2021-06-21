package com.github.muancmf.confluence.api;

import com.github.muancmf.confluence.model.Content;
import com.github.muancmf.confluence.model.Page;
import com.github.muancmf.confluence.publisher.ConfluencePublisher;
import joptsimple.internal.Strings;
import kong.unirest.Unirest;

public class ContentAPI extends BaseAPI {
    private static final String CONFLUENCE_CONTENT = "content";

    public ContentAPI(ConfluencePublisher publisher){
        super(publisher);
    }

    public Page findPageByNameFromSpace(String pageName) {
        return authorizedRequest(Unirest.get(getContentWith("search")))
                .queryString("cql", "type=page and title=" + pageName)
                .asObject(Page.class)
                .getBody();
    }

    public Content createContent(Content payload) {
        return authorizedRequestWithBody(Unirest.post(getContent()))
                .body(payload).asObject(Content.class).getBody();
    }

    public Content updateContent(Content payload) {
        payload.nextVersion();

        return authorizedRequestWithBody(Unirest.put(getContentWith(payload.getId())))
                .body(payload)
                .asObject(Content.class).getBody();
    }

    public Content getPageFullInfo(String pageId) {
        return authorizedRequest(Unirest.get(getContentWith(pageId)))
                .queryString("expand", "body.storage,version")
                .asObject(Content.class).getBody();
    }

    private String getContent() {
        return Strings.join(
                new String[]{
                        getURI(),
                        CONFLUENCE_CONTENT
                }, SEPARATOR);
    }

    private String getContentWith(String parameters) {
        return Strings.join(
                new String[]{
                        getURI(),
                        CONFLUENCE_CONTENT,
                        parameters
                }, SEPARATOR);
    }
}
