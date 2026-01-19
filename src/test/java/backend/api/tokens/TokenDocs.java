package backend.api.tokens;



import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class TokenDocs extends MockMvcRestDocs {
    

    public Map<String,Object> newEntity(){
        Map<String,Object> r = new HashMap<>();
        r.put("subject", randomText("Token"));
        return r;
    }
}
