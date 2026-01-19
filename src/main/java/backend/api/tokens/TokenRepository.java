package backend.api.tokens;

import backend.domain.Token;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;


public interface TokenRepository extends RestfulJpaRepository<Token,String>{
    
    // Optional<Token> findOneByTokenValue(String tokenValue);

}
