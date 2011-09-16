package com.gooddata.mongotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.data.document.mongodb.query.Criteria.where;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config-converter.xml"})
public class ConverterTest {

	@Autowired
	MongoOperations operations;
	
	@Test
	public void testInsertUsing() {
		operations.dropCollection(AutoId.class);
		AutoId auto = new AutoId(1978, "Skoda", new ObjectId().toString());
		operations.insert(auto);
		
		AutoId auto2 = operations.findOne(new Query(where("_id").is(auto.getId())), AutoId.class);
		assertThat(auto2.getId(), is(auto.getId()));
//		System.out.println(auto2);
	}
	
	public static class PersonWriteConverter implements Converter<AutoId, DBObject> {
		public DBObject convert(AutoId source) { 
			DBObject dbo = new BasicDBObject();
			dbo.put("_id", source.getId());
			dbo.put("datumVyroby", source.getDatumVyroby());
			dbo.put("znacka", source.getZnacka());
			dbo.put("ridic", source.getRidic());
			return dbo;
		}
	}

	public static class PersonReadConverter implements Converter<DBObject, AutoId> {
		public AutoId convert(DBObject source) {
			AutoId auto = new AutoId(new Integer(source.get("datumVyroby").toString()),
						 (String)source.get("znacka"), (String)source.get("_id"));
			auto.setRidic((String)source.get("znacka"));
			return auto;
		}
	}
	
}
