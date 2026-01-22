package backend;

import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.u2ware.common.oauth2.jwt.SimpleJwtAuthenticationConverter;
import io.u2ware.common.oauth2.jwt.SimpleJwtCodec;
import io.u2ware.common.oauth2.jwt.UserAuthoritiesConverter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

	protected Log logger = LogFactory.getLog(getClass());

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(30000l);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize ->{  

                    if(SimpleJwtCodec.available(op)) {                    
                        authorize.requestMatchers("/api/**").authenticated()
                        ;
                    }else{
                        authorize.requestMatchers("/api/**").permitAll()
                        ;
                    }
                    authorize.anyRequest().permitAll();
            })
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            )
            ;
        
        return http.build();
    }

    /////////////////////////////////////////////////////////
    // OAuth2ResourceServer
    /////////////////////////////////////////////////////////
    private @Autowired OAuth2ResourceServerProperties op;
    private @Autowired(required = false) UserAuthoritiesConverter userAuthoritiesConverter;

    @Bean 
    public JwtDecoder jwtDecoder() throws Exception{
        return new SimpleJwtCodec(op);
    }
    @Bean 
    public JwtAuthenticationConverter jwtConverter() {
        return new SimpleJwtAuthenticationConverter(userAuthoritiesConverter);
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver r = new DefaultBearerTokenResolver();
        r.setAllowUriQueryParameter(true); //?access_token GET only
        return r;
    }

}