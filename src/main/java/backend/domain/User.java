package backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import backend.domain.auditing.AuditedEntity;
import backend.domain.properties.AttributesSet;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "examples_users")
@Data @EqualsAndHashCode(callSuper = true)
public class User extends AuditedEntity {

    @Id
    private String userId;

    private AttributesSet roles = new AttributesSet();

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String searchKeyword;
}