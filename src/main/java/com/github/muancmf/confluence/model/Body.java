package com.github.muancmf.confluence.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Body {
    private Storage storage;
}