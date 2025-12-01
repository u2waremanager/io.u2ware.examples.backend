package backend.api.items;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;


@Component
public class ItemDocs extends MockMvcRestDocs {



    public Map<String,Object> newEntity(String foo){
        Map<String,Object> r = new HashMap<>();
        r.put("title", randomText("item"));
        r.put("fooLink", foo);
        return r;
    }

    public Map<String,Object> newEntity(Map<String,Object> foo){
        Map<String,Object> r = new HashMap<>();
        r.put("title", randomText("item"));
        r.put("fooLink", foo);
        return r;
    }

    
    public Map<String,Object> updateEntity(Map<String,Object> x, String foo){

        // System.err.println("updateEntity: "+r);

        Map<String,Object> r = new HashMap<>();
        r.put("title", randomText("itemUpdate"));
        r.put("fooLink", foo);
        // System.err.println("updateEntity: "+r);
        return r;
    }
    
    public Map<String,Object> updateEntity(Map<String,Object> r, Map<String,Object> foo){
        r.put("fooLink", foo);
        return r;
    }

}
