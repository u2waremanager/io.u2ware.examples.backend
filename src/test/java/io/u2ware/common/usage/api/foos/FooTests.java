package io.u2ware.common.usage.api.foos;

import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.print;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.u2ware.common.docs.MockMvcRestDocs.*;
import io.u2ware.common.usage.domain.Foo;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class FooTests {


	protected Log logger = LogFactory.getLog(getClass());



	@Autowired
	protected FooDocs fd;


	@Autowired
	protected MockMvc mvc;


	@Autowired
	protected FooRepository fooRepository;


	@SuppressWarnings("null")
	@Test
	void contextLoads1() throws Exception{

		fooRepository.save(new Foo("a",1));
		fooRepository.save(new Foo("b",2));


		// mvc.perform(GET("/api/profile/foos")).andExpect(is2xx()).andDo(print());

		// //////////////////////////////////////////////
		// //
		// //////////////////////////////////////////////
		// mvc.perform(GET("/api/foos")).andExpect(is2xx());
		// mvc.perform(GET("/api/foos/1")).andExpect(is2xx());
		// mvc.perform(GET("/api/foos/search")).andExpect(is2xx());
		// mvc.perform(GET("/api/foos/search/findByAge").param("age", "1")).andExpect(is2xx());


		// //////////////////////////////////////////////
		// //
		// //////////////////////////////////////////////
		// mvc.perform(POST("/api/foos/1")).andExpect(is4xx());
		// mvc.perform(POST("/api/foos/search")).andExpect(is4xx());



		mvc.perform(put("/api/foos/1")).andExpect(is2xx()).andDo(print());
	}



}
