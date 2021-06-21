package com.github.muancmf.confluence.publisher;

import com.github.muancmf.confluence.api.ContentAPI;
import com.github.muancmf.confluence.api.ContentBodyAPI;
import com.github.muancmf.confluence.model.AncestorsItem;
import com.github.muancmf.confluence.model.Body;
import com.github.muancmf.confluence.model.Content;
import com.github.muancmf.confluence.model.Space;
import com.github.muancmf.confluence.model.Storage;
import com.github.muancmf.confluence.model.Version;
import com.google.common.io.Files;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;

@Data
@Accessors(chain = true)
public class ConfluencePublisher {
    private String domainName;
    private String authentication;

    private ConfluencePublishOptions publishOptions;
    private ContentAPI contentApi;
    private ContentBodyAPI contentBodyApi;

    public ConfluencePublisher() {
        this.contentApi = new ContentAPI(this);
        this.contentBodyApi = new ContentBodyAPI(this);
    }

    public void publish() {
        var existPages = contentApi.findPageByNameFromSpace(publishOptions.getPageName());

        if (existPages.getResults().isEmpty()) {
            contentApi.createContent(newContent(null, null));
        } else {
            var existPageId = existPages.getResults().get(0).getId();
            var currentPage = contentApi.getPageFullInfo(existPageId);
            var newPage = contentBodyApi.convertWikiMarkupToStorageFormat(newStorage());

            if (!currentPage.getBody().getStorage().getComparableValue().equals(newPage.getComparableValue())) {
                contentApi.updateContent(newContent(existPageId, currentPage.getVersion()));
            }
        }
    }

    private Storage newStorage() {
        return new Storage()
                .setValue(getFileContent())
                .setRepresentation(publishOptions.getContentRepresentation());
    }

    private Content newContent(String currentId, Version currentVersion) {
        return new Content()
                .setTitle(publishOptions.getPageName())
                .setType("page")
                .setId(currentId)
                .setVersion(currentVersion)
                .setSpace(new Space()
                        .setKey(publishOptions.getSpaceName()))
                .setAncestors(Collections.singletonList(new AncestorsItem()
                        .setId(publishOptions.getRootPageId())))
                .setBody(new Body()
                        .setStorage(new Storage()
                                .setValue(getFileContent())
                                .setRepresentation(publishOptions.getContentRepresentation())));
    }

    private String getFileContent() {
        try {
            return Files.toString(new File(publishOptions.getContentPath()), UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
