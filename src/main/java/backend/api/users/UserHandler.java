package backend.api.users;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import backend.domain.User;
import backend.domain.auditing.AuditedAuditor;
import backend.domain.exception.ResponseStatusExceptions;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class UserHandler {
    
    protected Log logger = LogFactory.getLog(getClass());


    @HandleBeforeCreate
    public void HandleBeforeCreate(User e) throws Exception{
        logger.info("@HandleBeforeCreate : "+e);
        logger.info("@HandleBeforeCreate : "+e.getAuthorities());
        logger.info("@HandleBeforeCreate : "+AuditedAuditor.hasNotPermission("ROLE_ADMIN"));
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }

        if(! StringUtils.startsWithIgnoreCase(e.getPassword(), "{noop}"))
            e.setPassword("{noop}"+e.getPassword());
    }

    @HandleBeforeSave
    public void HandleBeforeSave(User e)throws Exception{
        logger.info("@HandleBeforeSave : "+e);
        logger.info("@HandleBeforeSave : "+e.getAuthorities());
        logger.info("@HandleBeforeSave : "+AuditedAuditor.hasNotPermission("ROLE_ADMIN"));
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }
        if(! StringUtils.startsWithIgnoreCase(e.getPassword(), "{noop}"))
            e.setPassword("{noop}"+e.getPassword());
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(User e)throws Exception{
        logger.info("@HandleBeforeDelete : "+e);
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }
    }


    @HandleAfterRead
    public void HandleAfterRead(User e, Serializable r)throws Exception{
        logger.info("@HandleAfterRead : "+e);

        if(! AuditedAuditor.isOwner(e.getInserted()) && AuditedAuditor.hasNotPermission(e.getInserted(), "ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }


    }


    @HandleBeforeRead
    public void HandleBeforeRead(User e, Specification<User> r)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
            throw ResponseStatusExceptions.UNAUTHORIZED;
        }
    }
}
