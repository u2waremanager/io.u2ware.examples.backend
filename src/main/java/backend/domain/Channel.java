package backend.domain;

import backend.domain.properties.AttributesMap;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examples_channel")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Channel {

    @Id
    private Long timestamp;

    private String principal;


    @Column(length = 1024*100)
    private AttributesMap payload;

}
