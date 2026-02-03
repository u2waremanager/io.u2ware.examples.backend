package backend.oauth2;


import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.is4xx;
import static io.u2ware.common.docs.MockMvcRestDocs.print;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import io.u2ware.common.oauth2.jwt.SimpleJwtCodec;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class Oauth2Tests {
    

    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired MockMvc mvc;
    protected @Autowired SimpleJwtCodec codec;

    
	protected @Autowired Oauth2Docs od;	


    @Test
    public void contextLoads() throws Exception{
        if(codec.available()) {
            contextLoadsWithOauth2();
        }else{
            contextLoadsWithoutOauth2();
        }
    }


    public void contextLoadsWithoutOauth2() throws Exception{
        Jwt u = od.jose("user1");
        mvc.perform(get("/api/profile")).andExpect(is2xx()).andDo(print());
        mvc.perform(get("/api/profile").auth(u)).andExpect(is2xx()).andDo(print());
    }

    public void contextLoadsWithOauth2() throws Exception{
        Jwt u = od.jose("user1");
        mvc.perform(get("/api/profile")).andExpect(is4xx()).andDo(print());
        mvc.perform(get("/api/profile").auth(u)).andExpect(is2xx()).andDo(print());
    }





    


	// @BeforeEach
	// public void setUp() {
	// 	this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	// 	this.webClient.getOptions().setRedirectEnabled(true);
	// 	this.webClient.getCookieManager().clearCookies();
	// 	// when(this.authorizationConsentService.findById(any(), any())).thenReturn(null);
	// }    


    // @Test
    // public void context2Loads() throws Exception{

    //     //Step 1
    //     mvc.perform(get("/oauth2/providers")).andDo(print()) .andDo(result(od::context, "providers")).andExpect(is2xx());
    //     String loginUrl = od.context("providers", "$.[0].uri");
    //     logger.info(loginUrl);

    //     //Step 2
	// 	HtmlPage loginPage = this.webClient.getPage(loginUrl);
    //     assertLoginPage(loginPage);

    //     //Step 3
    //     String username = properties.getUser().getName();
    //     String password = properties.getUser().getPassword();
    //     String logonPage = signIn(loginPage, username, password).getWebResponse().getContentAsString();
    //     logger.info(logonPage);


    //     //Step 4
    //     DocumentContext logonContext = JsonPath.parse(logonPage);
    //     logger.info(logonPage);
    //     String token = logonContext.read("$.id_token[0]");
    //     logger.info(token);


    //     //Step 5
    //     mvc.perform(get("/oauth2/userinfo").auth(token)).andDo(print()) .andExpect(is2xx());
    // }


    // ///////////////////////////////
    // // 
    // ///////////////////////////////
	// private static void assertLoginPage(HtmlPage page) {
	// 	assertThat(page.getUrl().toString()).endsWith("/login");

	// 	HtmlInput usernameInput = page.querySelector("input[name=\"username\"]");
	// 	HtmlInput passwordInput = page.querySelector("input[name=\"password\"]");
	// 	HtmlButton signInButton = page.querySelector("button");

	// 	assertThat(usernameInput).isNotNull();
	// 	assertThat(passwordInput).isNotNull();
	// 	assertThat(signInButton.getTextContent()).isEqualTo("Sign in");
	// }

	// private static <P extends Page> P signIn(HtmlPage page, String username, String password) throws IOException {
	// 	HtmlInput usernameInput = page.querySelector("input[name=\"username\"]");
	// 	HtmlInput passwordInput = page.querySelector("input[name=\"password\"]");
	// 	HtmlButton signInButton = page.querySelector("button");

	// 	usernameInput.type(username);
	// 	passwordInput.type(password);
	// 	return signInButton.click();
	// }

}
