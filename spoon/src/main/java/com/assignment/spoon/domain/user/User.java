package com.assignment.spoon.domain.user;

import com.assignment.spoon.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        DJ,
        LISTENER
    }

    public void changeStatus() {
        if (status.equals(Status.DJ)) {
            status = Status.LISTENER;
            return;
        }
        status = Status.DJ;
    }

    @Builder
    public User(Long id, String email, String password, Status status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
    }
}
