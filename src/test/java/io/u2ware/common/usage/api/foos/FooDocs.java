package io.u2ware.common.usage.api.foos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;
import io.u2ware.common.docs.RestDocumentationResultHandlerBuilder;


@Component
public class FooDocs extends MockMvcRestDocs {

    public Map<String,Object> newEntity(){

        Map<String,Object> r = new HashMap<>();
        r.put("name", "hello");
        r.put("age", 111);
        return r;
    }

    public Map<String,Object> searchEntity(){

        Map<String,Object> r = new HashMap<>();

        r.put("age", 111);
        return r;
    }




    public void searchDocs(RestDocumentationResultHandlerBuilder b) {

    }
}
