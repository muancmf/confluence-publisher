package com.github.muancmf.confluence.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Storage {
    private String value;
    private String representation;

    public String getComparableValue() {
        return this.value
                .replaceAll(" ac:macro-id=.{38}", "")
                .replaceAll("<br />", "<br/>")
                .trim();
    }
}