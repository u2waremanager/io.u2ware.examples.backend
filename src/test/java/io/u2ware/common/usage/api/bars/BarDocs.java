package io.u2ware.common.usage.api.bars;

import io.u2ware.common.docs.MockMvcRestDocs;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class BarDocs extends MockMvcRestDocs {

    public Map<String,Object> newEntity(){
        Map<String,Object> r = new HashMap<>();
        r.put("id", "hello"+System.currentTimeMillis());
        r.put("name", "hello");
        r.put("age", 111);
        return r;
    }

    public Map<String,Object> searchEntity(){
        Map<String,Object> r = new HashMap<>();

        r.put("age", 111);
        return r;
    }


}
