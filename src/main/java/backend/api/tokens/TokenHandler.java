package backend.api.tokens;



import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import backend.api.ResponseStatusExceptions;
import backend.domain.Token;
import backend.domain.auditing.AuditedAuditor;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;
import io.u2ware.common.oauth2.jose.JoseKeyEncryptor;

@Component
@RepositoryEventHandler
public class TokenHandler {
    
    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired JwtEncoder jwtEncoder;

    @HandleBeforeCreate
    public void HandleBeforeCreate(Token e) throws Exception{
        logger.info("@HandleBeforeCreate : "+e);
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }

        Jwt jwt = JoseKeyEncryptor.encrypt(jwtEncoder, claims->{
            claims.put("sub", e.getSubject());
            claims.put("email", e.getSubject());
            claims.put("name", e.getSubject());
            claims.put("oops", e.getSubject());
            claims.put("authorities", Arrays.asList());
        });
        e.setTimestamp(System.currentTimeMillis());
        e.setTokenValue(jwt.getTokenValue());
    }

    @HandleBeforeSave
    public void HandleBeforeSave(Token e)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Token e)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }


    @HandleAfterRead
    public void HandleAfterRead(Token e, Serializable r)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }


    @HandleBeforeRead
    public void HandleBeforeRead(Token e, Specification<Token> r)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }
    }
}
