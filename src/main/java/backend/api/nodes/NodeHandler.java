package backend.api.nodes;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Node;
import backend.domain.exception.ResponseStatusExceptions;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class NodeHandler {
    
    protected Log logger = LogFactory.getLog(getClass());


    @HandleBeforeCreate
    public void HandleBeforeCreate(Node e) throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }

    @HandleBeforeSave
    public void HandleBeforeSave(Node e)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Node e)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }


    @HandleAfterRead
    public void HandleAfterRead(Node e, Serializable r)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }


    @HandleBeforeRead
    public void HandleBeforeRead(Node e, Specification<Node> r)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        // if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
        //     throw ResponseStatusExceptions.UNAUTHORIZED;
        // }


    }
}
