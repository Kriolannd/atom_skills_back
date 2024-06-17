package ru.atomskills.back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Lesson {

    public Lesson(String code) {
        this.code = code;
    }

    @Id
    private String code;

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToMany
    private List<Trait> traits;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Supplement> supplement;

    @ManyToMany
    private List<Task> tasks;

    private String author;

    @ManyToMany(mappedBy = "lessons")
    private List<Topic> topics;

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
        private Lesson lesson;

        private String title;

        @Id
        private String file;

    };

}
