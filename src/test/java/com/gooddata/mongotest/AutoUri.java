package com.gooddata.mongotest;

public class AutoUri extends Auto {
	
	private final String uri;

	public AutoUri(Integer datumVyroby, String znacka, String uri) {
		super(datumVyroby, znacka);
		this.uri = uri;
	}
	
	public String getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return "AutoId [uri=" + uri + ", getDatumVyroby()=" + getDatumVyroby()
				+ ", getZnacka()=" + getZnacka() + "]";
	}
}
