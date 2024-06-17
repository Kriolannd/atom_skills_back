package ru.atomskills.back.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trait {

    @Id
    private String code;
    private String name;
    private String description;

    public Trait(String code) {
        this.code = code;
    }

}
