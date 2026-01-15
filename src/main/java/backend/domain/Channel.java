package backend.domain;

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
    private String principal;

    private Long timestamp;

    private String state;
}
