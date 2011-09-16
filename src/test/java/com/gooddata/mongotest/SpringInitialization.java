package com.gooddata.mongotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.data.document.mongodb.query.Criteria.where;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config.xml"})
public class SpringInitialization {

	@Autowired
	MongoOperations mongoOperations;
	
	@Test
	public void initWithSpring() {
		Auto auto = mongoOperations.findOne(new Query(where("datumVyroby").is(1978)), Auto.class);
		assertThat(auto.getDatumVyroby(), is(1978));
	}
	
}
