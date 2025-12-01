package backend.domain;



import java.util.Set;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import backend.domain.auditing.AuditedEntity;
import backend.domain.properties.AttributesMap;
import backend.domain.properties.AttributesSet;
import backend.domain.properties.Crypto;
import backend.domain.properties.LinkDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

    // private Crypto cryptoValue;

    @Column(length = 1024*100)
    private AttributesMap jsonValue;


    @Column(length = 1024*100)
    private AttributesSet arrayValue;

    //////////////////////////////////////
    //
    ////////////////////////////////////
    @Column(length = 1024*100)
    private Link linkValue;


    @ManyToOne @RestResource(exported = false)
	@JsonProperty(access = Access.READ_ONLY)
	private Foo foo;
	
    @Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private Link fooLink;  // {foo : "http://....."} or {foo : {id : "", name: "", _links : {}}}"

    @Column(length = 1024*100)
    private Set<Link> linksValue;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String searchKeyword;
}