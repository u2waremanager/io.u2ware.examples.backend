package backend.api.items;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Foo;
import backend.domain.Item;
import backend.domain.properties.LinkConversion;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class ItemHandler {
    
    protected Log logger = LogFactory.getLog(getClass());

    private @Autowired LinkConversion linkConversion;

	private void conversion(Item e) throws Exception{
		logger.info("conversion1 "+e.getTitle());
		logger.info("conversion1 "+e.getFoo());
		logger.info("conversion1 "+e.getFooLink());
		linkConversion.convertWithEntity(Foo.class, e.getFooLink(), ref->{e.setFoo(ref);});		
		logger.info("conversion2 "+e.getTitle());
		logger.info("conversion2 "+e.getFoo());
		logger.info("conversion2 "+e.getFooLink());
	}



    @HandleBeforeCreate
    public void HandleBeforeCreate(Item e) throws Exception{
        logger.info("@HandleBeforeCreate : "+e);
        conversion(e);
    }


    @HandleBeforeSave
    public void HandleBeforeSave(Item e)throws Exception{
        logger.info("@HandleBeforeSave : "+e.getTitle());
		logger.info("@HandleBeforeSave "+e.getFoo());
		logger.info("@HandleBeforeSave "+e.getFooLink());
        conversion(e);
    }

    @HandleBeforeDelete
    public void HandleBeforeDelete(Item e)throws Exception{
        logger.info("@HandleBeforeDelete : "+e);
    }



    // @HandleAfterRead
    // public void HandleAfterRead(Item e, Serializable r)throws Exception{
    //     logger.info("@HandleAfterRead : "+e);
    //     logger.info("@HandleAfterRead : "+r);
    // }


    @HandleBeforeRead
    public void HandleBeforeRead(Item e, Specification<Item> r)throws Exception{
        logger.info("@HandleBeforeRead : "+e);
        logger.info("@HandleBeforeRead : "+r);
    }
}
