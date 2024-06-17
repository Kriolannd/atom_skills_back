package ru.atomskills.back.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    public Task(String code) {
        this.code = code;
    }

    @Id
    private String code;

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Supplement> supplement;

    private Integer difficulty;

    private Integer time;

    @ManyToMany(mappedBy = "tasks")
    private List<Lesson> lessons;

    @Entity
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Supplement {

        @Id
        @ManyToOne
        @JsonIgnore
        private Task task;

        private String title;

        @Id
        private String file;

    };
}
