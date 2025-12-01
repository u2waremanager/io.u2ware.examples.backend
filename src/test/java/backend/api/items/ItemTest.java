package backend.api.items;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import backend.api.foos.FooDocs;
import backend.api.oauth2.Oauth2Docs;
import backend.domain.User;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ItemTest {
    
    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired MockMvc mvc;
    
	protected @Autowired Oauth2Docs od;	
	protected @Autowired FooDocs fd;

	protected @Autowired ItemDocs id;


    @Test
    public void contextLoads() throws Exception{


        Jwt u = od.jose("itemTestUser1");

        //////////////////////////////
        // 
        //////////////////////////////
   		mvc.perform(post("/api/foos").auth(u).content(fd::newEntity, "foo1"))
            .andDo(result(fd::context, "foo1"))
            .andExpect(is2xx());
        Map<String,Object> fooBody1 = fd.context("foo1", "$");
        String fooLink1 = fd.context("foo1", "$._links.self.href");


   		mvc.perform(post("/api/foos").auth(u).content(fd::newEntity, "foo2"))
            .andDo(result(fd::context, "foo2"))
            .andExpect(is2xx());
        Map<String,Object> fooBody2 = fd.context("foo2", "$");
        String fooLink2 = fd.context("foo2", "$._links.self.href");


        logger.info("fooBody1: "+fooBody1);
        logger.info("fooLink1: "+fooLink1);
        logger.info("fooBody2: "+fooBody2);
        logger.info("fooLink2: "+fooLink2);


        //////////////////////////////
        // Create
        //////////////////////////////
   		mvc.perform(post("/api/items").auth(u).content(id::newEntity, fooLink1))
            .andDo(result(id::context, "item1"))
            .andDo(print())
            .andExpect(is2xx());
        Map<String,Object> itemBody1 = id.context("item1", "$");
        String itemLink1 = id.context("item1", "$._links.self.href");
        logger.info(itemBody1);
        logger.info(itemLink1);


   		mvc.perform(post("/api/items").auth(u).content(id::newEntity, fooBody2))
            .andDo(result(id::context, "item2"))
            .andExpect(is2xx());
        Map<String,Object> itemBody2 = id.context("item2", "$");
        String itemLink2 = id.context("item2", "$._links.self.href");
        logger.info(itemBody2);
        logger.info(itemLink2);

        //////////////////////////////
        // Read
        //////////////////////////////
   		mvc.perform(post(itemLink1).auth(u))
            .andDo(print())
            .andExpect(is2xx());
        

        logger.info("========================================================================");
        logger.info("========================================================================");
        logger.info("========================================================================");
        logger.info("========================================================================");

        // //////////////////////////////
        // // Update
        // //////////////////////////////
   		mvc.perform(put(itemLink1).auth(u).content(id::updateEntity, itemBody1, fooBody2))
            .andDo(print())
            .andExpect(is2xx());






    }
    
}
