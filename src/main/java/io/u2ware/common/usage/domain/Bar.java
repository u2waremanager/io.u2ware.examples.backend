package io.u2ware.common.usage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Bar {

    public Bar() {

    }

    public Bar(String name, Integer age) {
        this.id = name;
        this.age = age;
    }

    @Id
    private String id;

    private String name;
    private Integer age;
}