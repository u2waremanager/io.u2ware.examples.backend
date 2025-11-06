package io.u2ware.common.usage.domain;



import java.util.UUID;

import io.u2ware.common.usage.domain.auditing.AuditedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
public class Item extends AuditedEntity{
    
    @Id
    @GeneratedValue
    private UUID id;

    private String organization;

    private String stringValue;

    private Integer integerValue;

}
