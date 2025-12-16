package backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.jwk.RSAKey;

import io.u2ware.common.oauth2.crypto.JoseKeyGenerator;

@Configuration
public class Oauth2DocsConfig {

    @Bean
    public RSAKey joseRsaKey(){
        return JoseKeyGenerator.generateRsa();
    }
}
