package io.u2ware.common.usage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.u2ware.common.usage.domain.auditing.AuditedEntity;
import io.u2ware.common.usage.domain.properties.Attributes;
import io.u2ware.common.usage.domain.properties.AttributesSet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
public class User extends AuditedEntity {

    @Id
    // @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "userId")
    private String userId;

    private String userGroup;

    private Long userTimestamp;

    private AttributesSet roles = new AttributesSet();

    @Column(length = 1024*10)
    private Attributes attributes;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String searchKeyword;
}