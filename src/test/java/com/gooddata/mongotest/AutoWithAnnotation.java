package com.gooddata.mongotest;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.document.mongodb.index.CompoundIndex;
import org.springframework.data.document.mongodb.index.CompoundIndexes;
import org.springframework.data.document.mongodb.index.Indexed;
import org.springframework.data.document.mongodb.mapping.Document;

@Document
@CompoundIndexes({
	@CompoundIndex(name = "cmp_index", def = "{'motor': 1, 'objem': -1}")
})
public class AutoWithAnnotation extends Auto {
	
	@Id
	private final String uri;
	
	@Indexed(unique=true)
	private String spz;
	
	private String motor;

	private String objem;
	
	@Transient
	private String barva;

	@PersistenceConstructor
	public AutoWithAnnotation(Integer datumVyroby, String znacka, String uri) {
		super(datumVyroby, znacka);
		this.uri = uri;
	}

	public AutoWithAnnotation(Integer datumVyroby, String znacka) {
		super(datumVyroby, znacka);
		this.uri = new ObjectId().toString();
	}
	
	public String getUri() {
		return uri;
	}

	public String getSpz() {
		return spz;
	}

	public void setSpz(String spz) {
		this.spz = spz;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getObjem() {
		return objem;
	}

	public void setObjem(String objem) {
		this.objem = objem;
	}

	public String getBarva() {
		return barva;
	}

	public void setBarva(String barva) {
		this.barva = barva;
	}

	@Override
	public String toString() {
		return "AutoWithAnnotation [uri=" + uri + ", spz=" + spz + ", motor="
				+ motor + ", objem=" + objem + ", barva=" + barva + "]";
	}
}
