package com.example.applog.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "applogs")
public class AppLog {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(name="message")
    private String message;

    @Column(name="time")
    private Long time;

}

