package com.gooddata.mongotest;

import org.springframework.data.annotation.Id;

public class AutoId extends Auto {

	@Id
	private final String id;
	
	public AutoId(Integer datumVyroby, String znacka, String id) {
		super(datumVyroby, znacka);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "AutoId [id=" + id + ", getDatumVyroby()=" + getDatumVyroby()
				+ ", getZnacka()=" + getZnacka() + "]";
	}
}
