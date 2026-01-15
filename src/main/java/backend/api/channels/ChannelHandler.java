package backend.api.channels;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.api.ResponseStatusExceptions;
import backend.domain.Channel;
import backend.domain.User;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class ChannelHandler {
    
    protected Log logger = LogFactory.getLog(getClass());


    @HandleBeforeCreate
    public void HandleBeforeCreate(Channel e) throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }

    @HandleBeforeSave
    public void HandleBeforeSave(Channel e)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Channel e)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }


    @HandleAfterRead
    public void HandleAfterRead(Channel e, Serializable r)throws Exception{
        throw ResponseStatusExceptions.NOT_FOUND;
    }


    @HandleBeforeRead
    public void HandleBeforeRead(Channel e, Specification<User> r)throws Exception{

    }
}
