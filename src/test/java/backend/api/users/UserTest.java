package backend.api.users;


import static io.u2ware.common.docs.MockMvcRestDocs.*;

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

import backend.api.oauth2.Oauth2Docs;
import backend.domain.User;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserTest {
    
    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired MockMvc mvc;
    protected @Autowired UserDocs ud;
    protected @Autowired Oauth2Docs od;


    protected @Autowired UserRepository userRepository;
    
    @Test
    public void contextLoads() throws Exception{

        Specification<User> spec = JpaSpecificationBuilder.of(User.class)
            .where()
            .and()
            .like("roles",  "%ROLE_ADMIN%")
            .build();
        Optional<User> user = userRepository.findOne(spec);
        boolean admin = user.map(u-> true).orElse(false );

        Jwt u1 = admin ? od.jose("u1") : od.jose("u1", "ROLE_ADMIN");
        Jwt u2 = od.jose("u2");
        Jwt u3 = od.jose("u3");
        

        // C
        mvc.perform(post("/api/users").content(ud::newEntity, "u3").auth(u3)).andExpect(is4xx());
        mvc.perform(post("/api/users").content(ud::newEntity, "u2").auth(u2)).andExpect(is4xx());
        mvc.perform(post("/api/users").content(ud::newEntity, "u1").auth(u1)).andExpect(is2xx()); //andDo(print());

        // R
        mvc.perform(post("/api/users/u1")).andExpect(is4xx());
        mvc.perform(post("/api/users/u1").auth(u3)).andExpect(is4xx());
        mvc.perform(post("/api/users/u1").auth(u2)).andExpect(is4xx());
        mvc.perform(post("/api/users/u1").auth(u1)).andExpect(is2xx());

        // U
        mvc.perform(post("/api/users/u1")).andExpect(is4xx());
        mvc.perform(post("/api/users/u1").content(ud::resetEntity).auth(u3)).andExpect(is4xx());
        mvc.perform(post("/api/users/u1").content(ud::resetEntity).auth(u2)).andExpect(is4xx());
        mvc.perform(post("/api/users/u1").content(ud::resetEntity).auth(u1)).andExpect(is2xx());

        // D
        mvc.perform(delete("/api/users/u1")).andExpect(is4xx());
        mvc.perform(delete("/api/users/u1").auth(u3)).andExpect(is4xx());
        mvc.perform(delete("/api/users/u1").auth(u2)).andExpect(is4xx());
        mvc.perform(delete("/api/users/u1").auth(u1)).andExpect(is2xx());

        // Search
        mvc.perform(post("/api/users/search")).andExpect(is4xx());
        mvc.perform(post("/api/users/search").auth(u3)).andExpect(is4xx());
        mvc.perform(post("/api/users/search").auth(u2)).andExpect(is4xx());
        mvc.perform(post("/api/users/search").auth(u1)).andExpect(is2xx());

    }
}
