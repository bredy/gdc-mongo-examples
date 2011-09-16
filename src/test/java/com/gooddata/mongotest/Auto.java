package com.gooddata.mongotest;

public class Auto {

	private final Integer datumVyroby;
	private final String znacka;
	private String ridic;

	public Auto(Integer datumVyroby, String znacka) {
		super();
		this.datumVyroby = datumVyroby;
		this.znacka = znacka;
	}

	public Integer getDatumVyroby() {
		return datumVyroby;
	}

	public String getZnacka() {
		return znacka;
	}

	@Override
	public String toString() {
		return "Auto [datumVyroby=" + datumVyroby + ", znacka=" + znacka + "]";
	}

	public String getRidic() {
		return ridic;
	}

	public void setRidic(String ridic) {
		this.ridic = ridic;
	}
}
