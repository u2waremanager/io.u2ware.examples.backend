package backend.api.accounts;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import backend.domain.Account;
import backend.domain.properties.AttributesSet;
import io.u2ware.common.oauth2.jwt.SimpleJwtGrantedAuthoritiesConverter;
import io.u2ware.common.oauth2.jwt.UserAuthoritiesConverter;

@Component
public class AccountService implements UserAuthoritiesConverter{
 
    
    protected Log logger = LogFactory.getLog(getClass());

    protected @Autowired AccountRepository accountRepository;


    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities = SimpleJwtGrantedAuthoritiesConverter.authorities(jwt);
        logger.info("authorities By jwt:    "+authorities);

        /////////////////////////////////////////
        //
        /////////////////////////////////////////
        String accountId = jwt.getClaim("provider")+"_"+jwt.getSubject();

        accountRepository.findById(accountId).ifPresentOrElse((a)->{
            AttributesSet roles = a.getRoles();
            Collection<GrantedAuthority> add = Account.getAuthorities(roles);
            logger.info("authorities By account:    "+add);
            authorities.addAll(add);

        }, ()->{
            logger.info("authorities By jwt:    "+accountId);

            AttributesSet roles = accountRepository.count() > 0 
                ? new AttributesSet(Arrays.asList("ROLE_USER")) 
                : new AttributesSet(Arrays.asList("ROLE_ADMIN"));

            Account a = new Account();
            a.setId(accountId);
            a.setUsername(jwt.getClaimAsString("name"));
            a.setProvider(jwt.getClaimAsString("provider"));            
            a.setRoles(roles);
            //For Auditing...
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
            //For Auditing...
            accountRepository.save(a);


            Collection<GrantedAuthority> add = Account.getAuthorities(roles);
            authorities.addAll(add);
        });        

        logger.info("authorities:    "+authorities);        
        return authorities;
    }

}
