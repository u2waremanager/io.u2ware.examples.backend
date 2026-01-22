package backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examples_sessions")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Node {


    @Id
    private String principal;

    private String state;

    private Long timestamp;

}
