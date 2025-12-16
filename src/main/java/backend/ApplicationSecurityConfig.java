package backend;

import java.nio.file.Path;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.ObjectUtils;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nimbusds.jose.jwk.RSAKey;

import io.u2ware.common.oauth2.crypto.CryptoKeyFiles;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

	protected Log logger = LogFactory.getLog(getClass());


    @Autowired
    private CorsConfigurationSource corsConfigurationSource;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors-> cors
                    .configurationSource(corsConfigurationSource)
            )
            .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions
                            .sameOrigin()
                    ))

            .authorizeHttpRequests(authorize -> 
                    authorize
                        .requestMatchers(HttpMethod.GET, "/api").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profile/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
            )
            .oauth2ResourceServer(
                    oauth2->oauth2.jwt(Customizer.withDefaults())
            );
        
        return http.build();
    }


    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver r = new DefaultBearerTokenResolver();
        r.setAllowUriQueryParameter(true); //?access_token GET only
        return r;
    }



    @Autowired(required = false)
    private Converter<Jwt, Collection<GrantedAuthority>> customJwtGrantedAuthoritiesConverter;

    
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = null;
        if(customJwtGrantedAuthoritiesConverter == null) {
            JwtGrantedAuthoritiesConverter c = new JwtGrantedAuthoritiesConverter();
            c.setAuthoritiesClaimName("authorities");
            c.setAuthorityPrefix("");
            jwtGrantedAuthoritiesConverter = c;
        }else{
            jwtGrantedAuthoritiesConverter = customJwtGrantedAuthoritiesConverter;
        }

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

	@Autowired
	private OAuth2ResourceServerProperties oauth2ResourceServerProperties;


    @Autowired(required = false)    
	private RSAKey joseRSAKey;


    @Bean
    public JwtDecoder jwtDecoder() throws Exception {

        if(! ObjectUtils.isEmpty(joseRSAKey)) {
            logger.info("NimbusJwtDecoder by joseRSAKey");
            return NimbusJwtDecoder.withPublicKey(joseRSAKey.toRSAPublicKey()).build(); 
        }

        Resource publicKeyLocation = oauth2ResourceServerProperties.getJwt().getPublicKeyLocation();
        String jwkSetUri = oauth2ResourceServerProperties.getJwt().getJwkSetUri();

        if(! ObjectUtils.isEmpty(publicKeyLocation)) {
            logger.info("NimbusJwtDecoder by publicKeyLocation");
            Path path = Path.of(publicKeyLocation.getURI());
            RSAPublicKey publicKey = CryptoKeyFiles.readRSAPublicKey(path);
            return NimbusJwtDecoder.withPublicKey(publicKey).build();
        }

        if(! ObjectUtils.isEmpty(jwkSetUri)) {
            logger.info("NimbusJwtDecoder by jwkSetUri");
            return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        }

        logger.info("NimbusJwtDecoder by Unimplemented");
        return new JwtDecoder(){
            public Jwt decode(String token) throws JwtException {
                throw new UnsupportedOperationException("Unimplemented method 'decode'");
            }
        };
    }
}