package com.github.muancmf.confluence.publisher;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfluencePublishOptions {
    private String spaceName;
    private String rootPageId;
    private String pageName;
    private String contentRepresentation;
    private String contentPath;
}
