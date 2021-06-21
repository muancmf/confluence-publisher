package com.github.muancmf.confluence.model;

import lombok.Data;

import java.util.List;

public @Data class Page {
	private int size;
	private int start;
	private int limit;
	private List<Result> results;
	private boolean value;
}