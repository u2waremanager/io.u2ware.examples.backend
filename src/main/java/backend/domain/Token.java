package backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "examples_tokens")
@Data 
public class Token  {
    
    @Id
    @Column(length = 1024*100)
    private String tokenValue;

    private String subject;

    private Long timestamp;
}
