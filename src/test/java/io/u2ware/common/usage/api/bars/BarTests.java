package io.u2ware.common.usage.api.bars;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import io.u2ware.common.usage.domain.Bar;

@SpringBootTest
@AutoConfigureMockMvc
public class BarTests {


	protected Log logger = LogFactory.getLog(getClass());



	@Autowired
	protected BarDocs bd;


	@Autowired
	protected MockMvc mvc;



	@Autowired
	protected BarRepository barRepository;

	@Test @SuppressWarnings("null")
	void contextLoads1() throws Exception{


		barRepository.save(new Bar("1",3));
		barRepository.save(new Bar("2",4));


		mvc.perform(get("/api/profile/bars")).andExpect(is2xx()).andDo(print());



		//////////////////////////////////////////////
		//
		//////////////////////////////////////////////
		mvc.perform(get("/api/bars")).andExpect(is4xx());
		mvc.perform(get("/api/bars/1")).andExpect(is4xx());
		mvc.perform(get("/api/bars/search")).andExpect(is4xx());
		mvc.perform(get("/api/bars/search/findByAge").param("age", "1")).andExpect(is4xx());

		//////////////////////////////////////////////
		//
		//////////////////////////////////////////////
		mvc.perform(post("/api/bars/1")).andExpect(is2xx());
		mvc.perform(post("/api/bars/search")).andExpect(is2xx());



	}



}
