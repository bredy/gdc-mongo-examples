package com.gooddata.mongotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.data.document.mongodb.query.Criteria.where;
import static org.springframework.data.document.mongodb.query.Query.query;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.data.document.mongodb.SimpleMongoDbFactory;
import org.springframework.data.document.mongodb.query.Index;
import org.springframework.data.document.mongodb.query.Order;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Update;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class SimpleInitialization {
	
	MongoOperations operations;
	
	@Before
	public void init() throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo("127.0.0.1");
		SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, "mongo");
		operations = new MongoTemplate(factory);
	}

	@Test
	public void ensureIndexOnCollection() {
		operations.ensureIndex(
                 new Index().on("znacka", Order.ASCENDING)
                 .on("rokVyroby", Order.ASCENDING)
                 .named("compound"), 
                 Auto.class
                 );
	}

	@Test
	public void insert() {
		operations.dropCollection(Auto.class);
		Auto auto = new Auto(1978, "Zil");
		operations.save(auto); // insert
		auto.setRidic("xxx");
		operations.save(auto);
	}
	
	@Test
	public void findOne() {
		operations.dropCollection(Auto.class);
		Auto auto = new Auto(1978, "Skoda");
		operations.insert(auto);
		
		Auto auto2 = operations.findOne(query(where("datumVyroby").is(1978).and("znacka").is("Skoda")), Auto.class);
		assertNotNull(auto2);
	}
	
	@Test
	public void findMany() {
		operations.dropCollection(Auto.class);
		Auto auto = new Auto(1978, "Skoda");
		Auto auto2 = new Auto(1980, "Audi");
		Auto auto3 = new Auto(1992, "Skoda");
		operations.insert(auto);
		operations.insert(auto2);
		operations.insert(auto3);
		List<Auto> auta = operations.find(query(where("datumVyroby").gt(1978)), Auto.class);
		assertThat(auta.size(), is(2));
	}
	
	@Test
	public void findObjectById() {
		operations.dropCollection(AutoId.class);
		AutoId auto = new AutoId(1978, "Skoda", new ObjectId().toString());
		operations.insert(auto);
		
		AutoId auto2 = operations.findById(new ObjectId(auto.getId()), AutoId.class);
		assertThat(auto2.getId(), is(auto.getId()));
	}
	
	@Test
	@Ignore
	public void updateWithoutId() {
		operations.dropCollection(Auto.class);
		Auto auto = new Auto(1978, "Skoda");
		Auto auto2 = new Auto(1980, "Audi");
		Auto auto3 = new Auto(1992, "Skoda");
		operations.insert(auto);
		operations.insert(auto2);
		operations.insert(auto3);

		Query query = new Query(where("datumVyroby").is(1978));
		Update update = new Update().set("znacka", "zzz");
		WriteResult result = operations.updateFirst(query, update, Auto.class);
		assertThat(result.getN(), is(1));
	}
	
	@Test
	public void updateWithId() {
		operations.dropCollection(AutoId.class);
		AutoId auto = new AutoId(1978, "Skoda", new ObjectId().toString());
		AutoId auto2 = new AutoId(1980, "Audi", new ObjectId().toString());
		AutoId auto3 = new AutoId(1992, "Skoda", new ObjectId().toString());
		operations.insert(auto);
		operations.insert(auto2);
		operations.insert(auto3);

		// first
		Query query = new Query(where("datumVyroby").is(1978));
		Update update = new Update().set("znacka", "zzz");
		WriteResult result = operations.updateFirst(query, update, AutoId.class);
		assertThat(result.getN(), is(1));
		
		// multi
		Query query2 = new Query(where("datumVyroby").gt(1978));
		Update update2 = new Update().inc("datumVyroby", 1);
		WriteResult result2 = operations.updateMulti(query2, update2, AutoId.class);
		assertThat(result2.getN(), is(2));
	}
	
	@Test
	public void removeWithId() {
		operations.dropCollection(AutoId.class);
		AutoId auto = new AutoId(1978, "Skoda", new ObjectId().toString());
		operations.insert(auto);
		
		AutoId auto2 = operations.findOne(query(where("datumVyroby").is(1978).and("znacka").is("Skoda")), AutoId.class);
		operations.remove(auto2);
	}
	
	@Test
	public void findObjectByUri() {
		operations.dropCollection(AutoUri.class);
		
//		limited ObjectId.isValid ... 24, a-zA-Z0-9 chars
//		AutoUri auto = new AutoUri(1978, "Skoda", new ObjectId("/gdc/md/auto/" + new ObjectId().toString()).toString());
//		AutoUri auto2 = operations.findById(new ObjectId(auto.getUri())), AutoUri.class);
		
		AutoUri auto = new AutoUri(1978, "Skoda", "/gdc/md/auto/" + new ObjectId().toString());
		operations.insert(auto);
		
		// _id
		AutoUri auto2 = operations.findOne(new Query(where("uri").is(auto.getUri())), AutoUri.class);
		assertThat(auto2.getUri(), is(auto.getUri()));
	}
}
