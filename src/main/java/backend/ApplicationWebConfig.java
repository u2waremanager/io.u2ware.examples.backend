package backend;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@Configuration
public class ApplicationWebConfig implements WebMvcConfigurer {

    protected Log logger = LogFactory.getLog(getClass());

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(false)
                .maxAge(30000);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(30000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        logger.info("addResourceHandlers "+registry);
        logger.info("frontendProperties "+frontendProperties);

        registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/META-INF/resources/")
                    .setCachePeriod(20)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver() {
                        @Override
                        protected Resource getResource(String resourcePath, Resource location) throws IOException {


                            logger.info("addResourceHandlers "+resourcePath);


                            Resource r = location.createRelative(resourcePath);
                            if(r.exists() && r.isReadable()){
                                return r;
                            } else{

                                //////////////////////////////
                                // Vue has spacialial uri
                                //////////////////////////////
                                // if(.....){
                                //     return new ClassPathResource("/io/u2ware/ocpp/index.html");
                                // }else{
                                //     return null;
                                // }

                                //////////////////////////////
                                // Vue has all uri
                                //////////////////////////////
                                ClassPathResource c = new ClassPathResource("/META-INF/resources/index.html");
                                if(c.exists() && c.isReadable()){
                                    return c;
                                } else{
                                    return null;
                                }
                            }
                        }
                    });
    }
 
    
    @Configuration
    @ConfigurationProperties(prefix="vite")
    public static class FrontendProperties extends HashMap<String,String>{}

    private @Autowired FrontendProperties frontendProperties;
    private @Autowired OAuth2ResourceServerProperties oauth2ResourceServerProperties;

    @RequestMapping(method=RequestMethod.OPTIONS, value="/env")
    public Object env(HttpServletRequest request){


        String oauth2 = "";
        if(StringUtils.hasText(oauth2ResourceServerProperties.getJwt().getJwkSetUri())) {
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(oauth2ResourceServerProperties.getJwt().getJwkSetUri()).build();
            String path = uriComponents.getPath();
            oauth2 = oauth2ResourceServerProperties.getJwt().getJwkSetUri().replaceAll(path, "");
        }else{
            oauth2 = "";
        }
        String sample = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();

        Map<String,String> p = new HashMap<>();
        p.put("oauth2.server", oauth2);
        p.put("rest.server", sample);
        p.put("rest.server.token", frontendProperties.get("rest.server.token"));


        return frontendProperties;
    }
}