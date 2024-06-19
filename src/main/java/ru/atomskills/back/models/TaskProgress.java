package ru.atomskills.back.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"task_code", "user_id"})
})
public class TaskProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private AppUser user;

    private Status status;

    private Long startTime;

    public enum Status {
        IN_PROGRESS, ON_REVIEW, REVIEWED
    }

//    @Getter
//    @Setter
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Id implements Serializable {
//        @ManyToOne
//        private Task task;
//
//        @ManyToOne
//        private AppUser user;
//
//        public static Id fromIds(String taskCode, Integer userId) {
//            return Id.builder()
//                    .task(Task.builder().code(taskCode).build())
//                    .user(AppUser.builder().id(userId).build())
//                    .build();
//        }
//    }
}
