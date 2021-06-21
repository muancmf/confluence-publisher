package com.github.muancmf.confluence.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Content {
    private String type;
    private String title;
    private Body body;
    private Version version;
    private Space space;
    private String id;
    private List<AncestorsItem> ancestors;

    public void nextVersion() {
        this.setVersion(new Version().setNumber(this.getVersion().getNumber() + 1));
    }
}