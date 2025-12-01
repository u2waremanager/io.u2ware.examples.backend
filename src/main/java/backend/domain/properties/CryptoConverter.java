package backend.domain.properties;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.crypto.SecretKey;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import io.u2ware.common.oauth2.crypto.CryptoEncryptor;
import io.u2ware.common.oauth2.crypto.CryptoKeyStore;
import jakarta.persistence.AttributeConverter;

// @Converter(autoApply = true)
public class CryptoConverter implements AttributeConverter<String, String> {

    private SecretKey secretKey;

    private SecretKey secretKey(){
        if(secretKey != null) return secretKey;
        try{
            String name = ClassUtils.getShortName(getClass());
            Resource resource = new ClassPathResource(name, getClass());
            Path path = Paths.get(resource.getURI());
            this.secretKey = CryptoKeyStore.load(path, "AES");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return secretKey;
    }


    @Override
    public String convertToDatabaseColumn(String attribute) {
        if(! StringUtils.hasText(attribute)) return null;
        try{
            return CryptoEncryptor.encrypt(attribute, secretKey());
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if(! StringUtils.hasText(dbData)) return null;
        try{
            return CryptoEncryptor.decrypt(dbData, secretKey());
        }catch(Exception e){
            return null;
        }
    }  
}
