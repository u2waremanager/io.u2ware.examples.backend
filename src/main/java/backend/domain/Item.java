package backend.domain;



import java.util.UUID;

import backend.domain.auditing.AuditedEntity;
import backend.domain.properties.Attributes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "examples_items")
@Data @EqualsAndHashCode(callSuper = true)
public class Item extends AuditedEntity{
    
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private Integer size;

    @Column(length = 1024*10)
    private Attributes attributes;

}

